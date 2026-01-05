
package com.Mrudul.SpringCrypto.service;

import com.Mrudul.SpringCrypto.dto.PortfolioDTO;
import com.Mrudul.SpringCrypto.entity.Portfolio;
import com.Mrudul.SpringCrypto.entity.User;
import com.Mrudul.SpringCrypto.repository.PortfolioRepository;
import com.Mrudul.SpringCrypto.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final BinanceWebSocketService binanceWebSocketService;

    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository,
                            BinanceWebSocketService binanceWebSocketService) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
        this.binanceWebSocketService = binanceWebSocketService;
    }

    public List<PortfolioDTO> getUserPortfolio(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Portfolio> portfolios = portfolioRepository.findByUserUserId(user.getUserId());
        return portfolios.stream().map(portfolio -> mapToPortfolioDTO(portfolio)).collect(Collectors.toList());
    }

    public Optional<Portfolio> getUserHolding(String username, String symbol) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return portfolioRepository.findByUserUserIdAndSymbol(user.getUserId(), symbol);
    }

    public BigDecimal getTotalPortfolioValue(String username) {
        List<PortfolioDTO> portfolios = getUserPortfolio(username);
        return portfolios.stream()
                .map(PortfolioDTO::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PortfolioDTO mapToPortfolioDTO(Portfolio portfolio) {
        PortfolioDTO dto = new PortfolioDTO();
        dto.setCoinSymbol(portfolio.getSymbol());
        dto.setCoinName(portfolio.getSymbol()); // Set coinName to be the same as coinSymbol
        dto.setQuantity(portfolio.getAmount());
        dto.setAverageBuyPrice(portfolio.getAverageBuyPrice());

        // Fetch real-time price
        BigDecimal currentPrice = binanceWebSocketService.fetchLivePrice(portfolio.getSymbol());
        dto.setCurrentPrice(currentPrice);

        // Calculate total value
        BigDecimal totalValue = currentPrice.multiply(portfolio.getAmount());
        dto.setTotalValue(totalValue.setScale(2, BigDecimal.ROUND_HALF_UP));

        // Calculate unrealized profit/loss
        BigDecimal unrealizedPL = (currentPrice.subtract(portfolio.getAverageBuyPrice()))
                .multiply(portfolio.getAmount())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        dto.setUnrealizedPL(unrealizedPL);

        return dto;
    }
}