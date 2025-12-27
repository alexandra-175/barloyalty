package com.example.gateway.controller;

import com.example.gateway.dto.BarRequest;
import com.example.gateway.entity.Bar;
import com.example.gateway.service.BarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bars")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class BarController {

    private final BarService barService;

    @GetMapping
    public List<Bar> getBars() {
        return barService.getAllBars();
    }

    @GetMapping("/{id}")
    public Bar getBar(@PathVariable UUID id) {
        return barService.getBar(id);
    }

    @PostMapping
    public Bar createBar(@RequestBody BarRequest request) {
        return barService.createBar(request);
    }

    @PutMapping("/{id}")
    public Bar updateBar(@PathVariable UUID id, @RequestBody BarRequest request) {
        return barService.updateBar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBar(@PathVariable UUID id) {
        barService.deleteBar(id);
    }
}
