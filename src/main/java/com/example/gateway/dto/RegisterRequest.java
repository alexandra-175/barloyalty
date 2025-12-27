package com.example.gateway.dto;

import com.example.gateway.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName;
    private Role role; // CLIENT sau BAR_OWNER
}
