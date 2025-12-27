package com.example.gateway.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoyaltyPointsRequest {
    private UUID userId;
    private UUID barId;
    private int points;
}
