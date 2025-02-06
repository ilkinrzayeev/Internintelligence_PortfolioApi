package com.example.portfolio_api1.controller;

import com.example.portfolio_api1.dto.request.AuthRequest;
import com.example.portfolio_api1.dto.request.UserRequest;
import com.example.portfolio_api1.dto.response.JwtResponse;
import com.example.portfolio_api1.dto.response.UserResponse;
import com.example.portfolio_api1.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticateAndGetToken(authRequest));
    }

    @PostMapping("register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(authService.registerUser(userRequest));
    }
}
