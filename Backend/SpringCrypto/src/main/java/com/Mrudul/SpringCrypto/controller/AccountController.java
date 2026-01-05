package com.Mrudul.SpringCrypto.controller;

import com.Mrudul.SpringCrypto.entity.User;
import com.Mrudul.SpringCrypto.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController // Marks this class as a REST API controller
@RequestMapping("/api/account") // Base URL for account-related endpoints
public class AccountController {

    private final UserService userService;

    // Constructor-based dependency injection of UserService
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the demo balance of the authenticated user.
     *
     * @param authentication The authentication object containing the logged-in user's details.
     * @return ResponseEntity containing the user's demo balance as a BigDecimal.
     */
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(Authentication authentication) {
        BigDecimal balance = userService.getUserByUsername(authentication.getName()).getDemoBalance();
        return ResponseEntity.ok(balance);
    }

    /**
     * Resets the demo balance of the authenticated user to the default value (10,000).
     *
     * @param authentication The authentication object containing the logged-in user's details.
     * @return ResponseEntity indicating the operation was successful.
     */
    @PostMapping("/balance")
    public ResponseEntity<Void> resetBalance(Authentication authentication) {
        // Fetch the authenticated user
        User user = userService.getUserByUsername(authentication.getName());

        // Reset the demo balance to the default value as a BigDecimal
        user.setDemoBalance(new BigDecimal("10000.00"));

        // Save the updated user details
        userService.registerUser(user);

        // Return a successful response with no content
        return ResponseEntity.ok().build();
    }
}