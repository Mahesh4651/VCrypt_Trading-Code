package com.Mrudul.SpringCrypto.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String email;
    private BigDecimal demoBalance = new BigDecimal("10000.00");

    @Column(nullable = false)
    private String firstName; // New field

    @Column(nullable = false)
    private String lastName;  // New field

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Portfolio> portfolioItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    // Existing explicit getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getDemoBalance() {
        return demoBalance;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDemoBalance(BigDecimal demoBalance) {
        this.demoBalance = demoBalance;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    // New explicit getters and setters for firstName and lastName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

   

}