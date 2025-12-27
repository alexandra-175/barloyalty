package com.example.gateway.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@RestController
@RequestMapping("/api/qr")
public class QrProxyController {

    private final WebClient webClient = WebClient.create();

    @GetMapping(
            value = "/bar/{barId}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] getQrFromService(@PathVariable UUID barId) {
        return webClient.get()
                .uri("http://barloyalty-qr:8080/qr/bar/{id}", barId)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }
}
