package com.example.gateway.security;

import com.example.gateway.dto.AuthResponse;
import com.example.gateway.dto.LoginRequest;
import com.example.gateway.dto.RegisterRequest;
import com.example.gateway.entity.User;
import com.example.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest req) {
        if (users.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(req.getEmail())
                .passwordHash(encoder.encode(req.getPassword()))
                .fullName(req.getFullName())
                .role(req.getRole())
                .build();

        users.save(user);

        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole()
        );

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId().toString())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );

        User user = users.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole()
        );

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId().toString())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
