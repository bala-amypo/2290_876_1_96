package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider tokenProvider;

    // ✅ REQUIRED BY TESTS
    public AuthServiceImpl(UserAccountRepository userRepo,
                           BCryptPasswordEncoder encoder,
                           JwtTokenProvider tokenProvider) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        String token = tokenProvider.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}
