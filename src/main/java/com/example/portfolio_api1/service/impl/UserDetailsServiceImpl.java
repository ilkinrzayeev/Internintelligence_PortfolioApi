package com.example.portfolio_api1.service.impl;
import com.example.portfolio_api1.entity.UserEntity;
import com.example.portfolio_api1.repository.UserRepository;
import com.example.portfolio_api1.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static com.example.portfolio_api1.enums.ErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND.getMessage()));
        return new CustomUserDetails(user);
    }
}