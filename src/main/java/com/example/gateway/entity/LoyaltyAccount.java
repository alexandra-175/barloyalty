package com.example.gateway.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "loyalty_accounts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "bar_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoyaltyAccount {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bar bar;

    private int pointsBalance;
}
