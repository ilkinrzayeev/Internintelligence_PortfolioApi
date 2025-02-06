package com.example.portfolio_api1.service;

import com.example.portfolio_api1.dto.request.UserRequest;
import com.example.portfolio_api1.dto.response.UserResponse;

import java.util.List;


public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Long userId, UserRequest userRequest, String username);

    void deleteUser(Long id, String username);

}