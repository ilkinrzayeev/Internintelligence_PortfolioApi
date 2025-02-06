package com.example.portfolio_api1.service.impl;
import com.example.portfolio_api1.dto.request.AuthRequest;
import com.example.portfolio_api1.dto.request.UserRequest;
import com.example.portfolio_api1.dto.response.JwtResponse;
import com.example.portfolio_api1.dto.response.UserResponse;
import com.example.portfolio_api1.entity.UserEntity;
import com.example.portfolio_api1.exceptions.AlreadyExistsException;
import com.example.portfolio_api1.repository.UserRepository;
import com.example.portfolio_api1.security.JwtUtil;
import com.example.portfolio_api1.service.AuthService;
import com.example.portfolio_api1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static com.example.portfolio_api1.enums.ErrorMessage.INVALID_USER_REQUEST;
import static com.example.portfolio_api1.enums.ErrorMessage.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;


    @Override
    public JwtResponse authenticateAndGetToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponse.builder()
                    .token(jwtUtil.generateToken(authRequest.getUsername())).build();
        } else {
            throw new UsernameNotFoundException(INVALID_USER_REQUEST.getMessage());
        }
    }

    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        UserEntity existingUser = userRepository.findByEmail(userRequest.getEmail())
                .orElse(null);
        if (existingUser != null && existingUser.getEmail() != null && existingUser.getEmail().isEmpty()) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS.getMessage());
        }
        return userService.createUser(userRequest);
    }
}
