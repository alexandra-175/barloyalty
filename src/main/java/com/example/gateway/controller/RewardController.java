package com.example.gateway.controller;

import com.example.gateway.dto.RewardRequest;
import com.example.gateway.entity.LoyaltyAccount;
import com.example.gateway.entity.Reward;
import com.example.gateway.service.RewardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class RewardController {

    private final RewardService rewardService;

    @PostMapping
    @PreAuthorize("hasAnyRole('BAR_OWNER','ADMIN')")
    public ResponseEntity<Reward> create(@RequestBody RewardRequest request) {
        return ResponseEntity.ok(rewardService.createReward(request));
    }

    @GetMapping("/bar/{barId}")
    public ResponseEntity<List<Reward>> getByBar(@PathVariable UUID barId) {
        return ResponseEntity.ok(rewardService.getRewardsForBar(barId));
    }

    @PostMapping("/redeem/{rewardId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<LoyaltyAccount> redeem(
            @PathVariable UUID rewardId,
            @RequestParam UUID userId
    ) {
        return ResponseEntity.ok(rewardService.redeemReward(userId, rewardId));
    }
}
