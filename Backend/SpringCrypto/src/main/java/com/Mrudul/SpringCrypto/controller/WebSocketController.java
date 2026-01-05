package com.Mrudul.SpringCrypto.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/general-subscribe") // ✅ Handles General WebSocket Subscriptions
    @SendTo("/topic/generalMessages") // ✅ Broadcasts to General Topic
    public String handleGeneralSubscription(String message) {
        System.out.println("📢 General WebSocket Subscription: " + message);
        return "Subscribed to /topic/generalMessages!";
    }
}
