package com.example.demo.dto;

public class AuthRequest {

    private String email;
    private String password;

    // REQUIRED by tests
    public AuthRequest() {}

    // getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // setters (🔥 REQUIRED — missing before)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
