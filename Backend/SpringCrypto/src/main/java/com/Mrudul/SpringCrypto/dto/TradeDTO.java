
package com.Mrudul.SpringCrypto.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TradeDTO {
    private Long tradeId;
    private Long userId;
    private String symbol;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal totalCost;
    private String type;
    private BigDecimal currantPriceOfCoin; // Typo in original: should be currentPriceOfCoin
    private BigDecimal profitLoss;
    private Date executedAt;
    private Date closedAt;
    
    
	public Date getClosedAt() {
		return closedAt;
	}
	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}
	public Date getExecutedAt() {
		return executedAt;
	}
	public void setExecutedAt(Date executedAt) {
		this.executedAt = executedAt;
	}
	public Long getTradeId() {
		return tradeId;
	}
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getCurrantPriceOfCoin() {
		return currantPriceOfCoin;
	}
	public void setCurrantPriceOfCoin(BigDecimal currantPriceOfCoin) {
		this.currantPriceOfCoin = currantPriceOfCoin;
	}
	public BigDecimal getProfitLoss() {
		return profitLoss;
	}
	public void setProfitLoss(BigDecimal profitLoss) {
		this.profitLoss = profitLoss;
	}   
}