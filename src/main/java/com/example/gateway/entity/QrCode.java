package com.example.gateway.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "qr_codes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QrCode {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    private Transaction transaction;

    private Instant expiresAt;

    private boolean used;

    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }
}
