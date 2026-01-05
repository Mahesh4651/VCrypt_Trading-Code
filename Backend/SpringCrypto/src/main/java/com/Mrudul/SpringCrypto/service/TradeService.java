package com.Mrudul.SpringCrypto.service;

import com.Mrudul.SpringCrypto.dto.TradeDTO;
import com.Mrudul.SpringCrypto.entity.Portfolio;
import com.Mrudul.SpringCrypto.entity.Trade;
import com.Mrudul.SpringCrypto.entity.Transaction;
import com.Mrudul.SpringCrypto.entity.User;
import com.Mrudul.SpringCrypto.repository.PortfolioRepository;
import com.Mrudul.SpringCrypto.repository.TradeRepository;
import com.Mrudul.SpringCrypto.repository.TransactionRepository;
import com.Mrudul.SpringCrypto.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final TransactionRepository transactionRepository;
    private final BinanceWebSocketService binanceWebSocketService;

    public TradeService(TradeRepository tradeRepository,
                        UserRepository userRepository,
                        PortfolioRepository portfolioRepository,
                        TransactionRepository transactionRepository,
                        BinanceWebSocketService binanceWebSocketService) {
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.transactionRepository = transactionRepository;
        this.binanceWebSocketService = binanceWebSocketService;
    }

    @Transactional
    public Trade executeTrade(String username, String symbol, String type, BigDecimal totalCost, BigDecimal amount) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        BigDecimal price = binanceWebSocketService.fetchLivePrice(symbol);
        BigDecimal calculatedAmount = amount != null ? amount : totalCost.divide(price, 8, RoundingMode.HALF_UP);
        BigDecimal calculatedTotalCost = totalCost != null ? totalCost : price.multiply(calculatedAmount);

        if ("BUY".equalsIgnoreCase(type)) {
            if (user.getDemoBalance().compareTo(calculatedTotalCost) < 0) {
                throw new IllegalArgumentException("Insufficient balance for this trade");
            }
            user.setDemoBalance(user.getDemoBalance().subtract(calculatedTotalCost));
            Trade trade = handleBuyTrade(user, symbol, calculatedAmount, price, calculatedTotalCost);
            userRepository.save(user);
            return tradeRepository.save(trade);
        } else if ("SELL".equalsIgnoreCase(type)) {
            Portfolio portfolio = portfolioRepository.findByUserUserIdAndSymbol(user.getUserId(), symbol)
                    .orElseThrow(() -> new IllegalArgumentException("You must buy " + symbol + " before selling"));
            if (portfolio.getAmount().compareTo(calculatedAmount) < 0) {
                throw new IllegalArgumentException("Insufficient " + symbol + " balance for this trade");
            }
            Trade trade = handleSellTrade(user, symbol, calculatedAmount, price, calculatedTotalCost, portfolio);
            user.setDemoBalance(user.getDemoBalance().add(calculatedTotalCost));
            userRepository.save(user);
            return tradeRepository.save(trade);
        } else {
            throw new IllegalArgumentException("Invalid trade type. Must be 'BUY' or 'SELL'");
        }
    }

    private Trade handleBuyTrade(User user, String symbol, BigDecimal amount, BigDecimal price, BigDecimal totalCost) {
        Optional<Trade> existingActiveTrade = tradeRepository.findFirstByUserUserIdAndSymbolAndStatus(user.getUserId(), symbol, "ACTIVE");

        Trade trade;
        Date now = new Date();
        if (existingActiveTrade.isPresent()) {
            trade = existingActiveTrade.get();
            BigDecimal oldTotalValue = trade.getAmount().multiply(trade.getPrice());
            BigDecimal newTotalValue = amount.multiply(price);
            BigDecimal totalAmount = trade.getAmount().add(amount);
            BigDecimal newAveragePrice = totalAmount.compareTo(BigDecimal.ZERO) > 0 ?
                    (oldTotalValue.add(newTotalValue)).divide(totalAmount, 8, RoundingMode.HALF_UP) : BigDecimal.ZERO;

            trade.setAmount(totalAmount);
            trade.setPrice(newAveragePrice);
            trade.setTotalCost(trade.getTotalCost().add(totalCost));
            // Keep status as ACTIVE and closed_at as NULL
        } else {
            trade = new Trade();
            trade.setUser(user);
            trade.setSymbol(symbol);
            trade.setType("BUY");
            trade.setAmount(amount);
            trade.setPrice(price);
            trade.setTotalCost(totalCost);
            trade.setStatus("ACTIVE"); // Start as ACTIVE
            trade.setExecutedAt(now);
            // closed_at remains NULL until fully sold
        }

        updatePortfolioForBuy(user, symbol, amount, price);
        createTransaction(user, "TRADE_BUY", totalCost.negate(),
                "Bought " + amount + " " + symbol + " at " + price);
        return trade;
    }

    private Trade handleSellTrade(User user, String symbol, BigDecimal amount, BigDecimal price, BigDecimal totalCost, Portfolio portfolio) {
        Trade activeTrade = tradeRepository.findFirstByUserUserIdAndSymbolAndStatus(user.getUserId(), symbol, "ACTIVE")
                .orElseThrow(() -> new IllegalArgumentException("No active buy trade found for " + symbol));

        BigDecimal remainingAmount = activeTrade.getAmount().subtract(amount);

        Trade sellTrade = new Trade();
        Date now = new Date();
        sellTrade.setUser(user);
        sellTrade.setSymbol(symbol);
        sellTrade.setType("SELL");
        sellTrade.setAmount(amount);
        sellTrade.setPrice(price);
        sellTrade.setTotalCost(totalCost);
        sellTrade.setStatus("COMPLETED");
        sellTrade.setExecutedAt(now);
        sellTrade.setClosedAt(now); // Set closed_at to executed_at for SELL
        sellTrade.setProfitLoss(calculateSellProfitLoss(activeTrade.getPrice(), price, amount));

        if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
            activeTrade.setStatus("COMPLETED");
            activeTrade.setClosedAt(now); // Set closed_at to executed_at when fully sold
            portfolioRepository.delete(portfolio);
        } else {
            activeTrade.setAmount(remainingAmount);
            activeTrade.setTotalCost(remainingAmount.multiply(activeTrade.getPrice()));
            updatePortfolioForSell(portfolio, amount, price);
        }

        tradeRepository.save(activeTrade);
        createTransaction(user, "TRADE_SELL", totalCost,
                "Sold " + amount + " " + symbol + " at " + price);
        return sellTrade;
    }

    private BigDecimal calculateSellProfitLoss(BigDecimal buyPrice, BigDecimal sellPrice, BigDecimal amount) {
        return (sellPrice.subtract(buyPrice)).multiply(amount).setScale(2, RoundingMode.HALF_UP);
    }

    private void updatePortfolioForBuy(User user, String symbol, BigDecimal amount, BigDecimal price) {
        Optional<Portfolio> existingPortfolio = portfolioRepository.findByUserUserIdAndSymbol(user.getUserId(), symbol);

        if (existingPortfolio.isPresent()) {
            Portfolio portfolio = existingPortfolio.get();
            BigDecimal oldValue = portfolio.getAmount().multiply(portfolio.getAverageBuyPrice());
            BigDecimal newValue = amount.multiply(price);
            BigDecimal totalAmount = portfolio.getAmount().add(amount);
            BigDecimal newAveragePrice = totalAmount.compareTo(BigDecimal.ZERO) > 0 ?
                    (oldValue.add(newValue)).divide(totalAmount, 8, RoundingMode.HALF_UP) : BigDecimal.ZERO;

            portfolio.setAmount(totalAmount);
            portfolio.setAverageBuyPrice(newAveragePrice);
            portfolio.setCurrentValue(totalAmount.multiply(price));
            portfolio.setLastUpdated(new Date());
            portfolioRepository.save(portfolio);
        } else {
            Portfolio portfolio = new Portfolio();
            portfolio.setUser(user);
            portfolio.setSymbol(symbol);
            portfolio.setAmount(amount);
            portfolio.setAverageBuyPrice(price);
            portfolio.setCurrentValue(amount.multiply(price));
            portfolio.setLastUpdated(new Date());
            portfolioRepository.save(portfolio);
        }
    }

    private void updatePortfolioForSell(Portfolio portfolio, BigDecimal amount, BigDecimal price) {
        BigDecimal remainingAmount = portfolio.getAmount().subtract(amount);
        portfolio.setAmount(remainingAmount);
        portfolio.setCurrentValue(remainingAmount.multiply(price));
        portfolio.setLastUpdated(new Date());
        portfolioRepository.save(portfolio);
    }

    private void createTransaction(User user, String type, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCreatedAt(new Date());
        transactionRepository.save(transaction);
    }

    // Other methods (completeTrade, updatePortfolioValues, etc.) remain unchanged
    // Include them as per the full implementation if needed


    @Transactional
    public Trade completeTrade(Long tradeId, String username, BigDecimal currentPrice) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!trade.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("Trade does not belong to this user");
        }
        if ("COMPLETED".equals(trade.getStatus())) {
            throw new IllegalArgumentException("Trade is already completed");
        }

        BigDecimal profitLoss;
        BigDecimal tradeValue = currentPrice.multiply(trade.getAmount());

        if ("BUY".equals(trade.getType())) {
            profitLoss = currentPrice.subtract(trade.getPrice()).multiply(trade.getAmount());
            user.setDemoBalance(user.getDemoBalance().add(tradeValue));
            Portfolio portfolio = portfolioRepository.findByUserUserIdAndSymbol(user.getUserId(), trade.getSymbol())
                    .orElseThrow(() -> new IllegalArgumentException("Portfolio entry not found for symbol: " + trade.getSymbol()));
            updatePortfolioForSell(portfolio, trade.getAmount(), currentPrice);
            createTransaction(user, "TRADE_SELL", tradeValue, "Sold " + trade.getAmount() + " " + trade.getSymbol() + " at " + currentPrice);
        } else {
            throw new IllegalArgumentException("Only BUY trades can be manually completed");
        }

        trade.setCurrentPrice(currentPrice);
        trade.setProfitLoss(profitLoss);
        trade.setStatus("COMPLETED");
        trade.setClosedAt(new Date());

        if (user.getDemoBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Insufficient funds to complete trade");
        }

        tradeRepository.save(trade);
        userRepository.save(user);
        return trade;
    }

    @Transactional
    public void updatePortfolioValues(String symbol, BigDecimal currentPrice) {
        List<Portfolio> portfolios = portfolioRepository.findAll().stream()
                .filter(p -> p.getSymbol().equals(symbol))
                .toList();

        for (Portfolio portfolio : portfolios) {
            BigDecimal newValue = portfolio.getAmount().multiply(currentPrice);
            portfolio.setCurrentValue(newValue);
            portfolio.setLastUpdated(new Date());
            portfolioRepository.save(portfolio);
        }
    }

    @Transactional
    public User addDemoFunds(String username, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setDemoBalance(user.getDemoBalance().add(amount));
        createTransaction(user, "DEPOSIT", amount, "Added demo funds");
        return userRepository.save(user);
    }

    public List<TradeDTO> getCompletedTrades(String username) {
    	User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Trade> trades = tradeRepository.findByUserUserIdAndStatus(user.getUserId(), "COMPLETED");
        return trades.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Trade getTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId).orElse(null);
    }

    public List<TradeDTO> getActiveTrades(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Trade> trades = tradeRepository.findByUserUserIdAndStatus(user.getUserId(), "ACTIVE");
        return trades.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private TradeDTO mapToDTO(Trade trade) {
        TradeDTO dto = new TradeDTO();
        dto.setTradeId(trade.getTradeId());
        dto.setUserId(trade.getUser().getUserId());
        dto.setSymbol(trade.getSymbol());
        dto.setAmount(trade.getAmount());
        dto.setPrice(trade.getPrice());
        dto.setTotalCost(trade.getTotalCost());
        dto.setType(trade.getType());
        dto.setCurrantPriceOfCoin(binanceWebSocketService.fetchLivePrice(trade.getSymbol()));
        dto.setProfitLoss(calculateProfitLoss(trade));
        dto.setExecutedAt(trade.getExecutedAt());   
        dto.setClosedAt(trade.getClosedAt());
        return dto;
    }

    private BigDecimal calculateProfitLoss(Trade trade) {
        if ("COMPLETED".equals(trade.getStatus()) && trade.getProfitLoss() != null) {
            return trade.getProfitLoss();
        }
        BigDecimal currentPrice;
        try {
            currentPrice = binanceWebSocketService.fetchLivePrice(trade.getSymbol());
        } catch (IllegalArgumentException e) {
            logger.warn("Could not fetch live price for symbol {}: {}", trade.getSymbol(), e.getMessage());
            return null;
        }
        if ("BUY".equalsIgnoreCase(trade.getType())) {
            return currentPrice.subtract(trade.getPrice()).multiply(trade.getAmount()).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO; // SELL trades have P&L calculated at execution
    }
}


