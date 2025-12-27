package com.example.gateway.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RewardRequest {

    private String name;
    private String description;
    private int pointsCost;
    private UUID barId;
}
