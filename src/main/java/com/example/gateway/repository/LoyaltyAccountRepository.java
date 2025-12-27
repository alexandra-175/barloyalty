package com.example.gateway.repository;

import com.example.gateway.entity.LoyaltyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoyaltyAccountRepository extends JpaRepository<LoyaltyAccount, UUID> {
    Optional<LoyaltyAccount> findByUserIdAndBarId(UUID userId, UUID barId);
    List<LoyaltyAccount> findByUserId(UUID userId);
    List<LoyaltyAccount> findByBarId(UUID barId);
}
