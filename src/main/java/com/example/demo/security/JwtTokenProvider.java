package com.example.demo.security;

import com.example.demo.model.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String generateToken(UserAccount user) {
        return "token";
    }

    public boolean validateToken(String token) {
        return true;
    }

    public Long getUserId(String token) {
        return 1L;
    }
}
