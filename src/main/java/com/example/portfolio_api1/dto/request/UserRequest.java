package com.example.portfolio_api1.dto.request;

import com.example.portfolio_api1.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 5, max = 30, message = "Username size must be between 5 and 30")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 5, max = 25,message = "Password size must be between 5 and 25")
    private String password;

    private RoleEnum role;
}