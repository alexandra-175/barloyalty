package com.example.gateway.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BarRequest {
    private String name;
    private String location;
    private UUID ownerId;
}
