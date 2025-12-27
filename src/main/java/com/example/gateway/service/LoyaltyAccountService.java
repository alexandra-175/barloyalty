package com.example.gateway.service;

import com.example.gateway.entity.LoyaltyAccount;
import com.example.gateway.entity.Bar;
import com.example.gateway.entity.User;
import com.example.gateway.repository.LoyaltyAccountRepository;
import com.example.gateway.repository.BarRepository;
import com.example.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoyaltyAccountService {

    private final LoyaltyAccountRepository loyaltyAccountRepository;
    private final UserRepository userRepository;
    private final BarRepository barRepository;

    public LoyaltyAccount createAccount(UUID userId, UUID barId) {
        // dacă există deja, îl returnăm direct
        return loyaltyAccountRepository.findByUserIdAndBarId(userId, barId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    Bar bar = barRepository.findById(barId)
                            .orElseThrow(() -> new RuntimeException("Bar not found"));

                    LoyaltyAccount account = new LoyaltyAccount();
                    account.setUser(user);
                    account.setBar(bar);
                    account.setPointsBalance(0);

                    return loyaltyAccountRepository.save(account);
                });
    }

    public List<LoyaltyAccount> getAccountsByBar(UUID barId) {
        return loyaltyAccountRepository.findByBarId(barId);
    }

    public List<LoyaltyAccount> getAccountsByUser(UUID userId) {
        return loyaltyAccountRepository.findByUserId(userId);
    }

    public LoyaltyAccount addPoints(UUID userId, UUID barId, int points) {
        if (points <= 0) {
            throw new IllegalArgumentException("Points should be positive");
        }

        LoyaltyAccount account = loyaltyAccountRepository
                .findByUserIdAndBarId(userId, barId)
                .orElseGet(() -> createAccount(userId, barId));

        account.setPointsBalance(account.getPointsBalance() + points);
        return loyaltyAccountRepository.save(account);
    }

    public LoyaltyAccount usePoints(UUID userId, UUID barId, int points) {
        if (points <= 0) {
            throw new IllegalArgumentException("Points should be positive");
        }

        LoyaltyAccount account = loyaltyAccountRepository
                .findByUserIdAndBarId(userId, barId)
                .orElseThrow(() -> new RuntimeException("Loyalty account not found"));

        if (account.getPointsBalance() < points) {
            throw new RuntimeException("Not enough points");
        }

        account.setPointsBalance(account.getPointsBalance() - points);
        return loyaltyAccountRepository.save(account);
    }
}
