package com.Mrudul.SpringCrypto.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CryptoWebSocketController {

    @MessageMapping("/crypto-subscribe") // ✅ Handles Crypto Price WebSocket Subscription
    @SendTo("/topic/cryptoPrices") // ✅ Broadcasts messages to subscribers
    public String handleCryptoSubscription(String message) {
        System.out.println("📢 New Crypto Subscription Request: " + message);
        return "Subscribed to /topic/cryptoPrices!";
    }
}
