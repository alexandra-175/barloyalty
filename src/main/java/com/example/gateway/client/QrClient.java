package com.example.gateway.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QrClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String QR_SERVICE_URL = "http://barloyalty-qr:8081";

    public byte[] generateQr() {
        return restTemplate.postForObject(
                QR_SERVICE_URL + "/qr/generate",
                null,
                byte[].class
        );
    }
}
