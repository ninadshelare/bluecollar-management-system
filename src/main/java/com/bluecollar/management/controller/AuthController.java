package com.bluecollar.management.controller;

import com.bluecollar.management.dto.LoginRequest;
import com.bluecollar.management.dto.LoginResponse;
import com.bluecollar.management.dto.RegisterRequest;
import com.bluecollar.management.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        boolean success = userService.login(request);

        if (!success) {
            return ResponseEntity
                    .status(401)
                    .body(new LoginResponse("Invalid credentials"));
        }

        return ResponseEntity.ok(new LoginResponse("Login successful"));
    }
}
