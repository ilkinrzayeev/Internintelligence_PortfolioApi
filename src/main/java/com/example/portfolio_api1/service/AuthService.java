package com.example.portfolio_api1.service;


import com.example.portfolio_api1.dto.request.AuthRequest;
import com.example.portfolio_api1.dto.request.UserRequest;
import com.example.portfolio_api1.dto.response.JwtResponse;
import com.example.portfolio_api1.dto.response.UserResponse;

public interface AuthService {
    JwtResponse authenticateAndGetToken(AuthRequest authRequest);

    UserResponse registerUser(UserRequest userRequest);
}
