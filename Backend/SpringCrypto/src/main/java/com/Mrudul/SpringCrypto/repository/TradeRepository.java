package com.Mrudul.SpringCrypto.repository;

import com.Mrudul.SpringCrypto.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByUserUserIdAndStatus(Long userId, String status);

    List<Trade> findByUserUserIdAndSymbolAndTypeAndStatus(Long userId, String symbol, String type, String status);

    List<Trade> findByUserUserIdAndSymbolAndStatus(Long userId, String symbol, String status);

    // Add this method to match TradeService's expectation of finding the first active trade
    Optional<Trade> findFirstByUserUserIdAndSymbolAndStatus(Long userId, String symbol, String status);
}