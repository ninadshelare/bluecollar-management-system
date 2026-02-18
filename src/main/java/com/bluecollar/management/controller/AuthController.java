package com.bluecollar.management.controller;

import com.bluecollar.management.dto.LoginRequest;
import com.bluecollar.management.dto.LoginResponse;
import com.bluecollar.management.dto.RegisterRequest;
import com.bluecollar.management.entity.User;
import com.bluecollar.management.security.jwt.JwtUtil;
import com.bluecollar.management.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        User user = userService.login(request);

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getRole().name()
        );

        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setToken(token);
        response.setMessage("Login successful");

        return ResponseEntity.ok(response);
    }
}