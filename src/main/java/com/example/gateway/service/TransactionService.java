package com.example.gateway.service;

import com.example.gateway.dto.TransactionRequest;
import com.example.gateway.entity.*;
import com.example.gateway.repository.*;
import com.example.gateway.service.websocket.LoyaltyEvent;
import com.example.gateway.service.websocket.LoyaltyEventPublisher;
import com.example.gateway.observability.TransactionMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final LoyaltyEventPublisher eventPublisher;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BarRepository barRepository;
    private final LoyaltyAccountService loyaltyAccountService;

    // 📊 METRICS
    private final TransactionMetrics transactionMetrics;

    public Transaction createTransaction(TransactionRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bar bar = barRepository.findById(request.getBarId())
                .orElseThrow(() -> new RuntimeException("Bar not found"));

        int points = (int) Math.floor(request.getAmount() * 0.1);

        loyaltyAccountService.addPoints(user.getId(), bar.getId(), points);

        Transaction transaction = Transaction.builder()
                .user(user)
                .bar(bar)
                .amount(request.getAmount())
                .pointsGiven(points)
                .createdAt(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        // 📊 INCREMENT METRIC
        transactionMetrics.increment();

        // 🔥 WEBSOCKET EVENT
        eventPublisher.send(
                new LoyaltyEvent(
                        user.getId(),
                        "TRANSACTION_VALIDATED",
                        points
                )
        );

        return saved;
    }

    public List<Transaction> getUserTransactions(UUID userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getBarTransactions(UUID barId) {
        return transactionRepository.findByBarId(barId);
    }
}
