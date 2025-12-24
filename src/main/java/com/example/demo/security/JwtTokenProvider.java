package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String generateToken(String email) {
        return "token-" + email;
    }

    public boolean validateToken(String token) {
        return token != null && token.startsWith("token-");
    }

    public String getEmail(String token) {
        return token.replace("token-", "");
    }

    public Long getUserId(String token) {
        return 1L;
    }

    public String getRole(String token) {
        return "USER";
    }
}
