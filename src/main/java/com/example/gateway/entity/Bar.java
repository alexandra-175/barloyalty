package com.example.gateway.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bars")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Bar {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }
}
