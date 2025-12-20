package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

    private final UserAccountRepository repo;

    public AuthServiceImpl(UserAccountRepository repo) {
        this.repo = repo;
    }

    public AuthResponse login(AuthRequest req) {

        UserAccount user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        return new AuthResponse("dummy-token");
    }
}
