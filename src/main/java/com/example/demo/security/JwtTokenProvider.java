package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public boolean validateToken(String token) {
        return true;
    }

    public String getUserId(String token) {
        return "1";
    }

    public String getEmail(String token) {
        return "test@example.com";
    }

    public String getRole(String token) {
        return "USER";
    }
}
