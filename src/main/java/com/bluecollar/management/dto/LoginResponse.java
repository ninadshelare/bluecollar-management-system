package com.bluecollar.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Long userId;
    private String name;
    private String email;
    private String role;
    private String message;

    // getters & setters
}

