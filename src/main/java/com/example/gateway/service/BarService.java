package com.example.gateway.service;

import com.example.gateway.dto.BarRequest;
import com.example.gateway.entity.Bar;
import com.example.gateway.entity.User;
import com.example.gateway.repository.BarRepository;
import com.example.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BarService {

    private final BarRepository barRepository;
    private final UserRepository userRepository;

    public List<Bar> getAllBars() {
        return barRepository.findAll();
    }

    public Bar getBar(UUID id) {
        return barRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bar not found"));
    }

    public Bar createBar(BarRequest request) {

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Bar bar = new Bar();
        bar.setName(request.getName());
        bar.setLocation(request.getLocation());
        bar.setOwner(owner);

        return barRepository.save(bar);
    }

    public Bar updateBar(UUID id, BarRequest request) {
        Bar existing = getBar(id);

        existing.setName(request.getName());
        existing.setLocation(request.getLocation());

        if (request.getOwnerId() != null) {
            User newOwner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            existing.setOwner(newOwner);
        }

        return barRepository.save(existing);
    }

    public void deleteBar(UUID id) {
        barRepository.deleteById(id);
    }
}
