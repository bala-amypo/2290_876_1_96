package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.AuthServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl service;

    public AuthController(AuthServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        return service.authenticate(req);
    }
}
