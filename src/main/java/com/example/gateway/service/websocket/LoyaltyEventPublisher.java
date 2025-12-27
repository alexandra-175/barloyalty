package com.example.gateway.service.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoyaltyEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void send(LoyaltyEvent event) {
        messagingTemplate.convertAndSend("/topic/loyalty", event);
    }
}
