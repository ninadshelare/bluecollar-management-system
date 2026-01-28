package com.bluecollar.management.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final String message;

    public LoginResponse(String message) {
        this.message = message;
    }
}
