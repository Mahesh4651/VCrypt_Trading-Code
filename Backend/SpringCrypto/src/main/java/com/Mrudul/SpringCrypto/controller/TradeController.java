//package com.Mrudul.SpringCrypto.controller;
//
//import com.Mrudul.SpringCrypto.dto.TradeDTO;
//import com.Mrudul.SpringCrypto.entity.Portfolio;
//import com.Mrudul.SpringCrypto.entity.Trade;
//import com.Mrudul.SpringCrypto.entity.User;
//import com.Mrudul.SpringCrypto.service.BinanceWebSocketService;
//import com.Mrudul.SpringCrypto.service.PortfolioService;
//import com.Mrudul.SpringCrypto.service.TradeService;
//import com.Mrudul.SpringCrypto.service.UserService;
//import lombok.Data;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/trades")
//public class TradeController {
//
//    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);
//
//    private final TradeService tradeService;
//    private final UserService userService;
//    private final PortfolioService portfolioService;
//    private final BinanceWebSocketService binanceWebSocketService;
//
//    public TradeController(TradeService tradeService, UserService userService,
//                           PortfolioService portfolioService, BinanceWebSocketService binanceWebSocketService) {
//        this.tradeService = tradeService;
//        this.userService = userService;
//        this.portfolioService = portfolioService;
//        this.binanceWebSocketService = binanceWebSocketService;
//    }
//
//    /**
//     * Execute a new trade (buy/sell) with real-world validations.
//     */
//    @PostMapping
//    public ResponseEntity<Map<String, Object>> executeTrade(
//            @RequestBody TradeRequest request,
//            Authentication authentication) {
//        try {
//            // Validate request
//            if (request == null || request.getSymbol() == null || request.getType() == null || request.getTotalCost() == null) {
//                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid trade request: symbol, type, and totalCost are required");
//            }
//            if (!"BUY".equalsIgnoreCase(request.getType()) && !"SELL".equalsIgnoreCase(request.getType())) {
//                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Trade type must be BUY or SELL");
//            }
//            if (request.getTotalCost().compareTo(BigDecimal.ZERO) <= 0) {
//                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Total cost must be positive");
//            }
//
//            String username = authentication.getName();
//            logger.info("Executing trade for user: {}, symbol: {}, type: {}, totalCost: {}",
//                    username, request.getSymbol(), request.getType(), request.getTotalCost());
//
//            Trade trade = tradeService.executeTrade(username, request.getSymbol().toUpperCase(),
//                    request.getType().toUpperCase(), request.getTotalCost());
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "Trade executed successfully");
//            response.put("trade", trade);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            logger.error("Trade execution failed: {}", e.getMessage());
//            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
//        } catch (Exception e) {
//            logger.error("Unexpected error during trade execution", e);
//            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//        }
//    }
//
//    /**
//     * Fetch active trades for the authenticated user.
//     */
//    @GetMapping("/active")
//    public ResponseEntity<List<TradeDTO>> getActiveTrades(Authentication authentication) {
//        try {
//            String username = authentication.getName();
//            logger.info("Fetching active trades for user: {}", username);
//            List<TradeDTO> trades = tradeService.getActiveTrades(username);
//            return ResponseEntity.ok(trades != null ? trades : Collections.emptyList());
//        } catch (Exception e) {
//            logger.error("Error fetching active trades", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
//        }
//    }
//
//    /**
//     * Fetch completed trades for the authenticated user.
//     */
//    @GetMapping("/completed")
//    public ResponseEntity<List<Trade>> getCompletedTrades(Authentication authentication) {
//        try {
//            String username = authentication.getName();
//            logger.info("Fetching completed trades for user: {}", username);
//            List<Trade> trades = tradeService.getCompletedTrades(username);
//            return ResponseEntity.ok(trades != null ? trades : Collections.emptyList());
//        } catch (Exception e) {
//            logger.error("Error fetching completed trades", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
//        }
//    }
//
//    /**
//     * Complete an existing trade using the current market price.
//     */
//    @PostMapping("/{tradeId}/complete")
//    public ResponseEntity<Map<String, Object>> completeTrade(
//            @PathVariable Long tradeId,
//            Authentication authentication) {
//        try {
//            if (tradeId == null || tradeId <= 0) {
//                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid trade ID");
//            }
//
//            String username = authentication.getName();
//            logger.info("Completing trade ID: {} for user: {}", tradeId, username);
//
//            Trade trade = tradeService.getTradeById(tradeId);
//            if (trade == null) {
//                return buildErrorResponse(HttpStatus.NOT_FOUND, "Trade not found");
//            }
//            if (!trade.getUser().getUsername().equals(authentication.getName())) {
//                return buildErrorResponse(HttpStatus.FORBIDDEN, "You are not authorized to complete this trade");
//            }
//            if ("COMPLETED".equals(trade.getStatus())) {
//                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Trade is already completed");
//            }
//
//            BigDecimal currentPrice = binanceWebSocketService.fetchLivePrice(trade.getSymbol());
//            if (currentPrice == null || currentPrice.compareTo(BigDecimal.ZERO) <= 0) {
//                return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Unable to fetch current market price");
//            }
//
//            Trade completedTrade = tradeService.completeTrade(tradeId, authentication.getName(), currentPrice);
//            User user = userService.getUserByUsername(authentication.getName()); // Fetch updated user
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "Trade completed successfully");
//            response.put("trade", completedTrade);
//            response.put("profitLoss", completedTrade.getProfitLoss());
//            response.put("newBalance", user.getDemoBalance());
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            logger.error("Trade completion failed: {}", e.getMessage());
//            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
//        } catch (Exception e) {
//            logger.error("Unexpected error during trade completion", e);
//            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//        }
//    }
//
//    /**
//     * Add demo funds to the user's account.
//     */
//    @PostMapping("/add-funds")
//    public ResponseEntity<Map<String, Object>> addDemoFunds(
//            @RequestBody AddFundsRequest request,
//            Authentication authentication) {
//        try {
//            if (request == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
//                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid amount: must be positive");
//            }
//
//            String username = authentication.getName();
//            logger.info("Adding demo funds for user: {}, amount: {}", username, request.getAmount());
//
//            User user = tradeService.addDemoFunds(username, request.getAmount());
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "Funds added successfully");
//            response.put("newBalance", user.getDemoBalance());
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            logger.error("Failed to add funds: {}", e.getMessage());
//            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
//        } catch (Exception e) {
//            logger.error("Unexpected error adding funds", e);
//            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//        }
//    }
//
//    /**
//     * Get the user's portfolio summary with real-time values.
//     */
//    @GetMapping("/portfolio")
//    public ResponseEntity<Map<String, Object>> getPortfolioSummary(Authentication authentication) {
//        try {
//            String username = authentication.getName();
//            logger.info("Fetching portfolio summary for user: {}", username);
//
//            User user = userService.getUserByUsername(username);
//            if (user == null) {
//                return buildErrorResponse(HttpStatus.NOT_FOUND, "User not found");
//            }
//
//            List<Portfolio> portfolio = portfolioService.getUserPortfolio(username);
//            BigDecimal totalPortfolioValue = portfolioService.getTotalPortfolioValue(username);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("demoBalance", user.getDemoBalance());
//            response.put("portfolio", portfolio != null ? portfolio : Collections.emptyList());
//            response.put("totalPortfolioValue", totalPortfolioValue != null ? totalPortfolioValue : BigDecimal.ZERO);
//            response.put("totalAssetValue", user.getDemoBalance().add(totalPortfolioValue != null ? totalPortfolioValue : BigDecimal.ZERO));
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            logger.error("Error fetching portfolio summary", e);
//            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//        }
//    }
//
//    /**
//     * Utility method to build error responses consistently.
//     */
//    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("error", true);
//        response.put("message", message);
//        return ResponseEntity.status(status).body(response);
//    }
//}
//
//@Data
//class TradeRequest {
//    private String symbol;
//    private String type;
//    private BigDecimal totalCost;
//}
//
//@Data
//class CompleteTradeRequest {
//    private BigDecimal currentPrice;
//}
//
//@Data
//class AddFundsRequest {
//    private BigDecimal amount;
//}


package com.Mrudul.SpringCrypto.controller;

import com.Mrudul.SpringCrypto.dto.PortfolioDTO;
import com.Mrudul.SpringCrypto.dto.TradeDTO;
import com.Mrudul.SpringCrypto.entity.Trade;
import com.Mrudul.SpringCrypto.entity.User;
import com.Mrudul.SpringCrypto.service.BinanceWebSocketService;
import com.Mrudul.SpringCrypto.service.PortfolioService;
import com.Mrudul.SpringCrypto.service.TradeService;
import com.Mrudul.SpringCrypto.service.UserService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

    private final TradeService tradeService;
    private final UserService userService;
    private final PortfolioService portfolioService;
    private final BinanceWebSocketService binanceWebSocketService;

    public TradeController(TradeService tradeService, UserService userService,
                           PortfolioService portfolioService, BinanceWebSocketService binanceWebSocketService) {
        this.tradeService = tradeService;
        this.userService = userService;
        this.portfolioService = portfolioService;
        this.binanceWebSocketService = binanceWebSocketService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> executeTrade(
            @RequestBody TradeRequest request,
            Authentication authentication) {
        try {
            if (request == null || request.getSymbol() == null || request.getType() == null) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid trade request: symbol and type are required");
            }
            if (!"BUY".equalsIgnoreCase(request.getType()) && !"SELL".equalsIgnoreCase(request.getType())) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Trade type must be BUY or SELL");
            }
            if ((request.getTotalCost() == null && request.getAmount() == null) ||
                    (request.getTotalCost() != null && request.getTotalCost().compareTo(BigDecimal.ZERO) <= 0) ||
                    (request.getAmount() != null && request.getAmount().compareTo(BigDecimal.ZERO) <= 0)) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Must provide valid totalCost or amount");
            }

            String username = authentication.getName();
            logger.info("Executing trade for user: {}, symbol: {}, type: {}, totalCost: {}, amount: {}",
                    username, request.getSymbol(), request.getType(), request.getTotalCost(), request.getAmount());

            Trade trade = tradeService.executeTrade(username, request.getSymbol().toUpperCase(),
                    request.getType().toUpperCase(), request.getTotalCost(), request.getAmount());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Trade executed successfully");
            response.put("trade", trade);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Trade execution failed: {}", e.getMessage());
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during trade execution", e);
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<TradeDTO>> getActiveTrades(Authentication authentication) {
        try {
            String username = authentication.getName();
            logger.info("Fetching active trades for user: {}", username);
            List<TradeDTO> trades = tradeService.getActiveTrades(username);
            return ResponseEntity.ok(trades != null ? trades : Collections.emptyList());
        } catch (Exception e) {
            logger.error("Error fetching active trades", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TradeDTO>> getCompletedTrades(Authentication authentication) {
//        try {
//            String username = authentication.getName();
//            logger.info("Fetching completed trades for user: {}", username);
//            List<Trade> trades = tradeService.getCompletedTrades(username);
//            return ResponseEntity.ok(trades != null ? trades : Collections.emptyList());
//        } catch (Exception e) {
//            logger.error("Error fetching completed trades", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
//        }
//
//
        try {
            String username = authentication.getName();
            logger.info("Fetching Completed trades for user: {}", username);
            List<TradeDTO> trades = tradeService.getCompletedTrades(username);
            return ResponseEntity.ok(trades != null ? trades : Collections.emptyList());
        } catch (Exception e) {
            logger.error("Error fetching Completed trades", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/{tradeId}/complete")
    public ResponseEntity<Map<String, Object>> completeTrade(
            @PathVariable Long tradeId,
            Authentication authentication) {
        try {
            if (tradeId == null || tradeId <= 0) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid trade ID");
            }

            String username = authentication.getName();
            logger.info("Completing trade ID: {} for user: {}", tradeId, username);

            Trade trade = tradeService.getTradeById(tradeId);
            if (trade == null) {
                return buildErrorResponse(HttpStatus.NOT_FOUND, "Trade not found");
            }
            if (!trade.getUser().getUsername().equals(username)) {
                return buildErrorResponse(HttpStatus.FORBIDDEN, "You are not authorized to complete this trade");
            }
            if ("COMPLETED".equals(trade.getStatus())) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Trade is already completed");
            }

            BigDecimal currentPrice = binanceWebSocketService.fetchLivePrice(trade.getSymbol());
            if (currentPrice == null || currentPrice.compareTo(BigDecimal.ZERO) <= 0) {
                return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Unable to fetch current market price");
            }

            Trade completedTrade = tradeService.completeTrade(tradeId, username, currentPrice);
            User user = userService.getUserByUsername(username);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Trade completed successfully");
            response.put("trade", completedTrade);
            response.put("profitLoss", completedTrade.getProfitLoss());
            response.put("newBalance", user.getDemoBalance());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Trade completion failed: {}", e.getMessage());
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during trade completion", e);
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @PostMapping("/add-funds")
    public ResponseEntity<Map<String, Object>> addDemoFunds(
            @RequestBody AddFundsRequest request,
            Authentication authentication) {
        try {
            if (request == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid amount: must be positive");
            }

            String username = authentication.getName();
            logger.info("Adding demo funds for user: {}, amount: {}", username, request.getAmount());

            User user = tradeService.addDemoFunds(username, request.getAmount());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Funds added successfully");
            response.put("newBalance", user.getDemoBalance());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to add funds: {}", e.getMessage());
            return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error adding funds", e);
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @GetMapping("/portfolio")
    public ResponseEntity<Map<String, Object>> getPortfolioSummary(Authentication authentication) {
        try {
            String username = authentication.getName();
            logger.info("Fetching portfolio summary for user: {}", username);

            User user = userService.getUserByUsername(username);
            if (user == null) {
                return buildErrorResponse(HttpStatus.NOT_FOUND, "User not found");
            }

            List<PortfolioDTO> portfolios = portfolioService.getUserPortfolio(username);
            BigDecimal totalPortfolioValue = portfolioService.getTotalPortfolioValue(username);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId().toString());
            response.put("demoBalance", user.getDemoBalance());
            response.put("totalPortfolioValue", totalPortfolioValue != null ? totalPortfolioValue : BigDecimal.ZERO);
            response.put("holdings", portfolios != null ? portfolios : Collections.emptyList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching portfolio summary", e);
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }



    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}

@Data
class TradeRequest {
    private String symbol;
    private String type;
    private BigDecimal totalCost;
    private BigDecimal amount;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


}

@Data
class AddFundsRequest {
    private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


}