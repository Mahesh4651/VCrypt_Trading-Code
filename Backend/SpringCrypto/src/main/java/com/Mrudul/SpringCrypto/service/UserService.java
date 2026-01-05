package com.Mrudul.SpringCrypto.service;

import com.Mrudul.SpringCrypto.entity.User;
import com.Mrudul.SpringCrypto.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * Service class for user-related operations, implementing Spring Security's UserDetailsService.
 * Handles user registration, retrieval, and updates, and provides user details for authentication.
 */
@Service
public class UserService implements UserDetailsService {

    // Repository for interacting with the users table in the database
    private final UserRepository userRepository;

    // Password encoder for securing passwords (injected with @Lazy to avoid circular dependency)
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for dependency injection.
     * @param userRepository Repository for user data access.
     * @param passwordEncoder Encoder for password hashing, lazily injected.
     */
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user with validation and password encoding.
     * @param user The User entity to register.
     * @return The saved User entity.
     * @throws IllegalArgumentException If username/email exists or required fields are missing.
     */
    @Transactional // Ensures database operations are atomic
    public User registerUser(User user) {
        // Check for existing username or email to prevent duplicates
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username or Email already exists!");
        }

        // Validate firstName and lastName to ensure they are provided
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required!");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required!");
        }

        // Encode the password before saving to ensure security
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database and return the persisted entity
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their username.
     * @param username The username to look up.
     * @return The User entity.
     * @throws IllegalArgumentException If the user is not found.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }

    /**
     * Retrieves a user by username or email.
     * @param identifier The username or email to search for.
     * @return An Optional containing the User if found, or empty if not.
     */
    public Optional<User> getUserByUsernameOrEmail(String identifier) {
        // First try to find by username, then fall back to email
        Optional<User> user = userRepository.findByUsername(identifier);
        return user.isPresent() ? user : userRepository.findByEmail(identifier);
    }

    /**
     * Implements UserDetailsService to load user details for Spring Security authentication.
     * @param username The username to load.
     * @return UserDetails object for authentication.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Return a Spring Security UserDetails object with username, password, and a default "USER" role
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );
    }

    /**
     * Updates an existing user's details in the database.
     * @param user The User entity with updated data.
     * @return The updated User entity.
     */
    @Transactional // Ensures the update is committed to the database
    public User updateUser(User user) {
        // Fetch the existing user by ID to ensure we update a managed entity
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + user.getUserId()));

        // Update the username (and other fields if needed)
        existingUser.setUsername(user.getUsername());

        // Save and return the updated entity
        return userRepository.save(existingUser);
    }
}