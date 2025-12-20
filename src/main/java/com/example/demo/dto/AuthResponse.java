package com.example.demo.dto;

public class AuthResponse {

    private String token;
    private Long userId;

    public AuthResponse(String token) {
        this.token = token;
        this.userId = 1L;
    }

    public String getToken() { return token; }
    public Long getUserId() { return userId; }
}
