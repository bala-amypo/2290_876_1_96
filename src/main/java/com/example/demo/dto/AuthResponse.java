package com.example.demo.dto;

public class AuthResponse {

    private String token;

    // REQUIRED by tests
    public AuthResponse() {}

    public AuthResponse(String token) {
        this.token = token;
    }

    // REQUIRED getter
    public String getToken() {
        return token;
    }

    // REQUIRED setter
    public void setToken(String token) {
        this.token = token;
    }
}
