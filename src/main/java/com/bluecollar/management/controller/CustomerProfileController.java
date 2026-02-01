package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bluecollar.management.dto.CustomerProfileRequestDTO;
import com.bluecollar.management.dto.CustomerProfileResponseDTO;
import com.bluecollar.management.service.CustomerProfileService;

@RestController
@RequestMapping("/api/customers")
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;

    public CustomerProfileController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    @GetMapping("/profile")
    public CustomerProfileResponseDTO getProfile(
            @RequestParam Long userId // TEMP until JWT
    ) {
        return customerProfileService.getProfile(userId);
    }

    @PostMapping("/profile")
    public CustomerProfileResponseDTO createProfile(
            @RequestParam Long userId, // TEMP until JWT
            @RequestBody CustomerProfileRequestDTO request
    ) {
        return customerProfileService.createProfile(userId, request);
    }

    @PutMapping("/profile")
    public CustomerProfileResponseDTO updateProfile(
            @RequestParam Long userId, // TEMP until JWT
            @RequestBody CustomerProfileRequestDTO request
    ) {
        return customerProfileService.updateProfile(userId, request);
    }

    @DeleteMapping("/profile")
    public String deleteProfile(
            @RequestParam Long userId // TEMP until JWT
    ) {
        customerProfileService.deleteProfile(userId);
        return "Customer profile deleted successfully";
    }
}

