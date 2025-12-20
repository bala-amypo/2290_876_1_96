package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;

public interface AuthService {

    // REQUIRED BY TESTS
    AuthResponse authenticate(AuthRequest request);

    // Backward compatibility (if login() exists elsewhere)
    default AuthResponse login(AuthRequest request) {
        return authenticate(request);
    }
}
