package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;

import com.example.demo.dto.AuthResponse;

public interface AuthService {

    AuthResponse authenticate(AuthRequest request); // ✅ REQUIRED
    void register(RegisterRequest request); 
}
