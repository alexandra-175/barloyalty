package com.example.gateway.service.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LoyaltyEvent {
    private UUID userId;
    private String type;
    private int points;
}
