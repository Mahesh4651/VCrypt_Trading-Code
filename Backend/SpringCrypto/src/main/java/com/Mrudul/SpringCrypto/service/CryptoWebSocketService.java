package com.Mrudul.SpringCrypto.service;

import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.binance.connector.client.utils.websocketcallback.WebSocketMessageCallback;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CryptoWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketStreamClientImpl client;
    private Set<String> availableSymbols = new HashSet<>();
    // Hardcoded list of top 30 coins by market cap (approximate, as of 2025)
    private final Set<String> topCoinSymbols = new HashSet<>(Arrays.asList(
            "BTC", "ETH", "BNB", "SOL", "XRP", "ADA", "AVAX", "DOGE", "TRX", "SHIB",
            "LINK", "DOT", "BCH", "NEAR", "LTC", "MATIC", "UNI", "ICP", "FET", "XLM",
            "HBAR", "VET", "CRO", "ATOM", "AAVE", "INJ", "MKR", "GRT", "RUNE", "ALGO"
    ));

    public CryptoWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.client = new WebSocketStreamClientImpl("wss://stream.binance.us:9443/ws");
        fetchAllSymbols();
        connectToBinance();
    }

    /**
     * Fetch all available USDT & USD trading pairs dynamically from Binance.US API
     */
    private void fetchAllSymbols() {
        try {
            String url = "https://api.binance.us/api/v3/exchangeInfo";
            RestTemplate restTemplate = new RestTemplate();
            JSONObject response = new JSONObject(restTemplate.getForObject(url, String.class));

            JSONArray symbolsArray = response.getJSONArray("symbols");
            for (int i = 0; i < symbolsArray.length(); i++) {
                JSONObject symbolObject = symbolsArray.getJSONObject(i);
                String symbol = symbolObject.getString("symbol").toLowerCase();
                if (symbol.endsWith("usdt") || symbol.endsWith("usd")) {
                    availableSymbols.add(symbol);
                }
            }
            System.out.println("✅ Fetched " + availableSymbols.size() + " symbols from Binance.US");
        } catch (Exception e) {
            System.err.println("❌ Error fetching trading pairs from Binance.US: " + e.getMessage());
        }
    }

    /**
     * Subscribe to Binance.US WebSocket for real-time updates
     */
    private void connectToBinance() {
        WebSocketMessageCallback callback = message -> {
            try {
                JSONArray tickers = new JSONArray(message);
                for (int i = 0; i < tickers.length(); i++) {
                    JSONObject ticker = tickers.getJSONObject(i);
                    String receivedSymbol = ticker.getString("s").toLowerCase();

                    if (availableSymbols.contains(receivedSymbol)) {
                        String price = ticker.getString("c");
                        String coinSymbol = receivedSymbol.replace("usdt", "").replace("usd", "").toUpperCase();
                        if (coinSymbol.equals("")) {
                            coinSymbol = "USDT";
                        }

                        // Only process updates for top 30 coins
                        if (topCoinSymbols.contains(coinSymbol)) {
                            String priceChange24h = ticker.has("P") ? ticker.getString("P") : "0.00";
                            String quoteVolume = ticker.getString("q");
                            String baseVolume = ticker.getString("v");
                            String coinOpen = ticker.getString("o");
                            String coinClose = ticker.getString("c");
                            String coinHigh = ticker.getString("h");
                            String coinLow = ticker.getString("l");
                            String imageUrl = CoinImageMapper.getImageUrl(coinSymbol);

                            Map<String, Object> finalData = new HashMap<>();
                            finalData.put("symbol", coinSymbol);
                            finalData.put("price", price);
                            finalData.put("image", imageUrl);
                            finalData.put("priceChange24h", priceChange24h);
                            finalData.put("quoteVolume", quoteVolume);
                            finalData.put("baseVolume", baseVolume);
                            finalData.put("coinOpen", coinOpen);
                            finalData.put("coinClose", coinClose);
                            finalData.put("coinHigh", coinHigh);
                            finalData.put("coinLow", coinLow);

                            messagingTemplate.convertAndSend("/topic/cryptoPrices", finalData);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        client.allMiniTickerStream(callback);
        System.out.println("✅ Subscribed to Binance.US tickers for top 30 coins");
    }
}







//
//package com.Mrudul.SpringCrypto.service;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.*;
//
//@Service
//public class CryptoWebSocketService {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final Set<String> availableSymbols = new HashSet<>();
//    private final Set<String> topCoinSymbols = new HashSet<>(Arrays.asList(
//            "BTC", "ETH", "BNB", "SOL", "XRP", "ADA", "AVAX", "DOGE", "TRX", "SHIB",
//            "LINK", "DOT", "BCH", "NEAR", "LTC", "MATIC", "UNI", "ICP", "FET", "XLM",
//            "HBAR", "VET", "CRO", "ATOM", "AAVE", "INJ", "MKR", "GRT", "RUNE", "ALGO"
//    ));
//
//    public CryptoWebSocketService(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//        fetchAllSymbols();
//        connectToBinance(); // Now initializes periodic polling
//    }
//
//    /**
//     * Fetch all available USDT & USD trading pairs dynamically from Binance.US API
//     */
//    private void fetchAllSymbols() {
//        try {
//            String url = "https://api.binance.us/api/v3/exchangeInfo";
//            RestTemplate restTemplate = new RestTemplate();
//            JSONObject response = new JSONObject(restTemplate.getForObject(url, String.class));
//
//            JSONArray symbolsArray = response.getJSONArray("symbols");
//            for (int i = 0; i < symbolsArray.length(); i++) {
//                JSONObject symbolObject = symbolsArray.getJSONObject(i);
//                String symbol = symbolObject.getString("symbol").toLowerCase();
//                if (symbol.endsWith("usdt") || symbol.endsWith("usd")) {
//                    availableSymbols.add(symbol);
//                }
//            }
//            System.out.println("✅ Fetched " + availableSymbols.size() + " symbols from Binance.US");
//        } catch (Exception e) {
//            System.err.println("❌ Error fetching trading pairs from Binance.US: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Periodically poll Binance API for real-time updates (replacing WebSocket)
//     */
//    private void connectToBinance() {
//        System.out.println("✅ Started polling Binance.US for top 30 coin prices");
//        // Scheduler will take care of periodic execution
//    }
//
//    /**
//     * Poll prices every 10 seconds (adjust if needed)
//     */
//    @Scheduled(fixedRate = 10000)
//    public void pollTopCoinPrices() {
//        try {
//            String url = "https://api.binance.us/api/v3/ticker/24hr";
//            RestTemplate restTemplate = new RestTemplate();
//            JSONArray response = new JSONArray(restTemplate.getForObject(url, String.class));
//
//            for (int i = 0; i < response.length(); i++) {
//                JSONObject ticker = response.getJSONObject(i);
//                String receivedSymbol = ticker.getString("symbol").toLowerCase();
//
//                if (availableSymbols.contains(receivedSymbol)) {
//                    String coinSymbol = receivedSymbol.replace("usdt", "").replace("usd", "").toUpperCase();
//                    if (coinSymbol.equals("")) {
//                        coinSymbol = "USDT";
//                    }
//
//                    if (topCoinSymbols.contains(coinSymbol)) {
//                        String price = ticker.getString("lastPrice");
//                        String priceChange24h = ticker.has("priceChangePercent") ? ticker.getString("priceChangePercent") : "0.00";
//                        String quoteVolume = ticker.getString("quoteVolume");
//                        String baseVolume = ticker.getString("volume");
//                        String coinOpen = ticker.getString("openPrice");
//                        String coinClose = ticker.getString("lastPrice");
//                        String coinHigh = ticker.getString("highPrice");
//                        String coinLow = ticker.getString("lowPrice");
//                        String imageUrl = CoinImageMapper.getImageUrl(coinSymbol);
//
//                        Map<String, Object> finalData = new HashMap<>();
//                        finalData.put("symbol", coinSymbol);
//                        finalData.put("price", price);
//                        finalData.put("image", imageUrl);
//                        finalData.put("priceChange24h", priceChange24h);
//                        finalData.put("quoteVolume", quoteVolume);
//                        finalData.put("baseVolume", baseVolume);
//                        finalData.put("coinOpen", coinOpen);
//                        finalData.put("coinClose", coinClose);
//                        finalData.put("coinHigh", coinHigh);
//                        finalData.put("coinLow", coinLow);
//
//                        messagingTemplate.convertAndSend("/topic/cryptoPrices", finalData);
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            System.err.println("❌ Error polling price data: " + e.getMessage());
//        }
//    }
//}
