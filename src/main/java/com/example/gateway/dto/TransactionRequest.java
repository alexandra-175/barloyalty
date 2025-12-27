package com.example.gateway.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TransactionRequest {
    private UUID userId;
    private UUID barId;
    private int amount;   // suma în lei
}
