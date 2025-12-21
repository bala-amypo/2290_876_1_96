package com.example.demo.dto;

public class AuthResponse {

    private String token;
    private Long userId;
    private String role;

    // ✅ REQUIRED BY TESTS
    public AuthResponse(String token, Long userId, String role) {
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
