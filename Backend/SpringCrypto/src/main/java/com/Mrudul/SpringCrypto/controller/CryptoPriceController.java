package com.Mrudul.SpringCrypto.controller;

import com.Mrudul.SpringCrypto.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Map;

@Controller
public class CryptoPriceController {

    private final SimpMessagingTemplate messagingTemplate;
    private final TradeService tradeService;

    @Autowired
    public CryptoPriceController(SimpMessagingTemplate messagingTemplate, TradeService tradeService) {
        this.messagingTemplate = messagingTemplate;
        this.tradeService = tradeService;
    }

    @MessageMapping("/price-updates")
    @SendTo("/topic/cryptoPrices")
    public Map<String, Object> processPriceUpdate(Map<String, Object> priceData) {
        String symbol = (String) priceData.get("symbol");
        String priceStr = (String) priceData.get("price");
        BigDecimal currentPrice = new BigDecimal(priceStr);

        tradeService.updatePortfolioValues(symbol, currentPrice);
        return priceData;
    }

    @MessageMapping("/request-price")
    @SendTo("/topic/cryptoPrices")
    public PriceUpdateResponse requestPriceUpdate(PriceRequest request) {
        BigDecimal simulatedPrice = BigDecimal.valueOf(100.00);
        String symbol = request.getSymbol();

        tradeService.updatePortfolioValues(symbol, simulatedPrice);

        PriceUpdateResponse response = new PriceUpdateResponse();
        response.setSymbol(symbol);
        response.setPrice(simulatedPrice);
        response.setTimestamp(System.currentTimeMillis());

        return response;
    }

    public void notifyPortfolioUpdate(String symbol, BigDecimal currentPrice) {
        tradeService.updatePortfolioValues(symbol, currentPrice);
        messagingTemplate.convertAndSend("/topic/portfolioUpdates",
                Map.of("symbol", symbol, "updatedPrice", currentPrice.toString()));
    }
}

class PriceRequest {
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

class PriceUpdateResponse {
    private String symbol;
    private BigDecimal price;
    private long timestamp;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}