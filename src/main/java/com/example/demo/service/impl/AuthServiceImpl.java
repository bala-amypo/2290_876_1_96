package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

    private final UserAccountRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwt;

    public AuthServiceImpl(UserAccountRepository repo,
                           BCryptPasswordEncoder encoder,
                           JwtTokenProvider jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthResponse authenticate(AuthRequest req) {
        String token = jwt.generateToken(req.username);
        return new AuthResponse(token);
    }
}
