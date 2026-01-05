package com.Mrudul.SpringCrypto.controller;

import com.Mrudul.SpringCrypto.entity.User;
import com.Mrudul.SpringCrypto.service.PortfolioService;
import com.Mrudul.SpringCrypto.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;

    public UserController(UserService userService, PortfolioService portfolioService) {
        this.userService = userService;
        this.portfolioService = portfolioService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        BigDecimal portfolioValue = portfolioService.getTotalPortfolioValue(authentication.getName());

        Map<String, Object> profile = new HashMap<>();
        profile.put("userId", user.getUserId());
        profile.put("firstName", user.getFirstName());
        profile.put("lastName", user.getLastName());
        profile.put("username", user.getUsername());
        profile.put("email", user.getEmail());
        profile.put("demoBalance", user.getDemoBalance());
        profile.put("portfolioValue", portfolioValue);
        profile.put("totalAssetValue", user.getDemoBalance().add(portfolioValue));

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody User updatedUser, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        user.setEmail(updatedUser.getEmail());

        return ResponseEntity.ok(userService.registerUser(user));
    }
}