package com.Mrudul.SpringCrypto.repository;

import com.Mrudul.SpringCrypto.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUserUserId(Long userId);

    Optional<Portfolio> findByUserUserIdAndSymbol(Long userId, String symbol);
}