package com.example.gateway.repository;

import com.example.gateway.entity.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BarRepository extends JpaRepository<Bar, UUID> {
}
