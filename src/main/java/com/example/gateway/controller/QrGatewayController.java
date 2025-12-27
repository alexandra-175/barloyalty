package com.example.gateway.controller;

import com.example.gateway.client.QrClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QrGatewayController {

    private final QrClient qrClient;

    @PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQr() {
        return ResponseEntity.ok(qrClient.generateQr());
    }
}
