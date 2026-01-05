package com.Mrudul.SpringCrypto.controller;

import com.Mrudul.SpringCrypto.entity.User;
import com.Mrudul.SpringCrypto.service.UserService;
import com.Mrudul.SpringCrypto.util.JwtUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * RestController for handling authentication-related requests such as registration, login, and username updates.
 * Maps all endpoints under "/api/auth".
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            System.out.println("🔹 Registering user: " + request.getUsername());
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            User registeredUser = userService.registerUser(user);
            String token = jwtUtil.generateToken(registeredUser.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (IllegalArgumentException e) {
            System.out.println("🔴 Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("🔴 Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        System.out.println("🔹 Login attempt for: " + request.getUsername() + " with raw password: " + request.getPassword());
        Optional<User> foundUser = userService.getUserByUsernameOrEmail(request.getUsername());
        if (foundUser.isEmpty()) {
            System.out.println("🔴 User not found: " + request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        System.out.println("🔹 Stored encoded password for " + foundUser.get().getUsername() + ": " + foundUser.get().getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            if (!authentication.isAuthenticated()) {
                System.out.println("🔴 Authentication failed for: " + request.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
            String token = jwtUtil.generateToken(foundUser.get().getUsername());
            System.out.println("✅ Login successful for: " + request.getUsername() + " | Token: " + token);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            System.out.println("🔴 Login error for " + request.getUsername() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    /**
     * Handles username updates for authenticated users.
     * Endpoint: POST /api/auth/updateusername
     * Validates the current password and updates the username, returning a new JWT token.
     */
    @PostMapping("/updateusername")
    public ResponseEntity<String> updateUsername(@RequestBody UpdateUsername request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("🔴 No authenticated user found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String currentUsername = authentication.getName();
        Optional<User> foundUser = userService.getUserByUsernameOrEmail(currentUsername);
        if (foundUser.isEmpty()) {
            System.out.println("🔴 User not found: " + currentUsername);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = foundUser.get();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(currentUsername, request.getPassword())
            );
        } catch (Exception e) {
            System.out.println("🔴 Invalid password for: " + currentUsername);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        Optional<User> existingUserWithNewUsername = userService.getUserByUsernameOrEmail(request.getNewUsername());
        if (existingUserWithNewUsername.isPresent()) {
            System.out.println("🔴 New username already taken: " + request.getNewUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        }
        System.out.println("asssas");

        // Update the username (userId is already set from the fetched entity)
        user.setUsername(request.getNewUsername());
        User updatedUser = userService.updateUser(user); // This now works with @Transactional

        String newToken = jwtUtil.generateToken(request.getNewUsername());
        System.out.println("✅ Username updated to: " + updatedUser.getUsername() + " | New Token: " + newToken);
        return ResponseEntity.ok(newToken);
    }

    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        private String firstName;
        private String lastName;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }

    @Data
    public static class UpdateUsername {
        private String username;    // Current username (optional, ignored in favor of JWT)
        private String password;    // Current password for validation
        private String newUsername; // Desired new username

        public String getNewUsername() { return newUsername; }
        public void setNewUsername(String newUsername) { this.newUsername = newUsername; }
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
        
        
    }
}