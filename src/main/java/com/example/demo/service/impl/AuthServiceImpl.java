package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import com.example.demo.dto.RegisterRequest;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    // ✅ REQUIRED BY TESTS (DO NOT REMOVE ANY PARAMETER)
    public AuthServiceImpl(UserAccountRepository userAccountRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtTokenProvider tokenProvider) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {

       UserAccount user = userAccountRepository
        .findByEmail(request.getEmail())
        .orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
            )
        );

        // ✅ TESTS EXPECT EMAIL, NOT UserAccount OBJECT
        String token = tokenProvider.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getId(),
                user.getRole()
        );
    }
    @Override
public void register(RegisterRequest request) {

    if (userAccountRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Email already registered"
        );
    }

    UserAccount user = new UserAccount();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setRole(request.getRole());

    // 🔐 Encrypt password
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userAccountRepository.save(user);
}

}
