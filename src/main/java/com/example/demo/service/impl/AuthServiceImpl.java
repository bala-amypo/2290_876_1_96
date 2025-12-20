package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository repo;

    public AuthServiceImpl(UserAccountRepository repo) {
        this.repo = repo;
    }

    @Override
    public AuthResponse login(AuthRequest req) {
        return new AuthResponse("dummy-token");
    }
}
