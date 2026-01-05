package com.Mrudul.SpringCrypto.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "trades")
@Data // Lombok annotation to generate getters, setters, toString, etc.
@NoArgsConstructor // Lombok annotation for no-args constructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long tradeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "type", nullable = false)
    private String type; // "BUY" or "SELL"

    @Column(name = "amount", nullable = false, precision = 20, scale = 8)
    private BigDecimal amount;

    @Column(name = "price", nullable = false)
    private BigDecimal price; // Price at execution

    @Column(name = "total_cost", nullable = false) // New field for total cost of the trade
    private BigDecimal totalCost;

    @Column(name = "current_price") // Price at completion
    private BigDecimal currentPrice;

    @Column(name = "profit_loss")
    private BigDecimal profitLoss;

    @Column(name = "status", nullable = false)
    private String status; // "ACTIVE" or "COMPLETED" (updated to match service)

    @Column(name = "executed_at") // New field for execution timestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date executedAt;

    @Column(name = "closed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedAt;

    // Constructor for creating a new trade
    public Trade(User user, String symbol, String type, BigDecimal amount, BigDecimal price, BigDecimal totalCost) {
        this.user = user;
        this.symbol = symbol;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.totalCost = totalCost;
        this.status = "ACTIVE"; // Updated to match service
        this.executedAt = new Date(); // Updated to match service
    }

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(BigDecimal profitLoss) {
		this.profitLoss = profitLoss;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getExecutedAt() {
		return executedAt;
	}

	public void setExecutedAt(Date executedAt) {
		this.executedAt = executedAt;
	}

	public Date getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}
    
    
}