package com.bluecollar.management.service;

import com.bluecollar.management.dto.LoginRequest;
import com.bluecollar.management.dto.RegisterRequest;
import com.bluecollar.management.entity.User;
import com.bluecollar.management.entity.enums.UserStatus;
import com.bluecollar.management.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
    }

    public boolean login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );
    }
}
