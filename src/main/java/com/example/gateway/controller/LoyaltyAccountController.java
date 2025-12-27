package com.example.gateway.controller;

import com.example.gateway.dto.LoyaltyPointsRequest;
import com.example.gateway.entity.LoyaltyAccount;
import com.example.gateway.service.LoyaltyAccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loyalty")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class LoyaltyAccountController {

    private final LoyaltyAccountService loyaltyAccountService;

    // Creează sau returnează un account pentru user+bar (de obicei la prima vizită)
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('BAR_OWNER','ADMIN')")
    public ResponseEntity<LoyaltyAccount> createAccount(
            @RequestParam UUID userId,
            @RequestParam UUID barId
    ) {
        return ResponseEntity.ok(loyaltyAccountService.createAccount(userId, barId));
    }

    // Toți clienții unui bar (vizibil de bar owner / admin)
    @GetMapping("/bar/{barId}")
    @PreAuthorize("hasAnyRole('BAR_OWNER','ADMIN')")
    public ResponseEntity<List<LoyaltyAccount>> getByBar(@PathVariable UUID barId) {
        return ResponseEntity.ok(loyaltyAccountService.getAccountsByBar(barId));
    }

    // Toate conturile unui user (clientul își vede punctele peste tot)
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('CLIENT','BAR_OWNER','ADMIN')")
    public ResponseEntity<List<LoyaltyAccount>> getByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(loyaltyAccountService.getAccountsByUser(userId));
    }

    // Adăugare puncte (folosit de tranzacții sau direct de bar)
    @PostMapping("/add-points")
    @PreAuthorize("hasAnyRole('BAR_OWNER','ADMIN')")
    public ResponseEntity<LoyaltyAccount> addPoints(@RequestBody LoyaltyPointsRequest request) {
        return ResponseEntity.ok(
                loyaltyAccountService.addPoints(
                        request.getUserId(),
                        request.getBarId(),
                        request.getPoints()
                )
        );
    }

    // Folosire puncte (de ex. la reward)
    @PostMapping("/use-points")
    @PreAuthorize("hasAnyRole('BAR_OWNER','ADMIN')")
    public ResponseEntity<LoyaltyAccount> usePoints(@RequestBody LoyaltyPointsRequest request) {
        return ResponseEntity.ok(
                loyaltyAccountService.usePoints(
                        request.getUserId(),
                        request.getBarId(),
                        request.getPoints()
                )
        );
    }
}
