package com.example.gateway.service;

import com.example.gateway.dto.RewardRequest;
import com.example.gateway.entity.*;
import com.example.gateway.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;
    private final BarRepository barRepository;
    private final LoyaltyAccountService loyaltyAccountService;

    public Reward createReward(RewardRequest request) {
        Bar bar = barRepository.findById(request.getBarId())
                .orElseThrow(() -> new RuntimeException("Bar not found"));

        Reward reward = Reward.builder()
                .name(request.getName())
                .description(request.getDescription())
                .pointsCost(request.getPointsCost())
                .bar(bar)
                .build();

        return rewardRepository.save(reward);
    }

    public List<Reward> getRewardsForBar(UUID barId) {
        return rewardRepository.findByBarId(barId);
    }

    public Reward getReward(UUID rewardId) {
        return rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Reward not found"));
    }

    public LoyaltyAccount redeemReward(UUID userId, UUID rewardId) {

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        // scădem punctele
        return loyaltyAccountService.usePoints(
                userId,
                reward.getBar().getId(),
                reward.getPointsCost()
        );
    }
}
