package com.example.portfolio_api1.service.impl;
import com.example.portfolio_api1.dto.request.UserRequest;
import com.example.portfolio_api1.dto.response.UserResponse;
import com.example.portfolio_api1.entity.UserEntity;
import com.example.portfolio_api1.exceptions.AlreadyExistsException;
import com.example.portfolio_api1.exceptions.NotFoundException;
import com.example.portfolio_api1.exceptions.UnauthorizedException;
import com.example.portfolio_api1.mapper.UserMapper;
import com.example.portfolio_api1.repository.UserRepository;
import com.example.portfolio_api1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.portfolio_api1.enums.ErrorMessage.*;
import static com.example.portfolio_api1.enums.RoleEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), id)
                ));
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS.getMessage());
        }

        UserEntity user = userMapper.toEntity(userRequest);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest, String username) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), userId)
                ));

        if (!hasPermission(username, user)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        if (!user.getUsername().equals(userRequest.getUsername()) &&
                userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS.getMessage());
        }

        userMapper.updateEntity(userRequest, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId, String username) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), userId)
                ));

        if (!hasPermission(username, user)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }
        userRepository.delete(user);
    }

    private boolean hasPermission(String username, UserEntity userEntity) {
        return userEntity.getUsername().equals(username) ||
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(USER_NOT_FOUND.getMessage(), username)
                        )).getRole().equals(ADMIN);
    }
}