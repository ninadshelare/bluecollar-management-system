package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.CustomerProfileRequestDTO;
import com.bluecollar.management.dto.CustomerProfileResponseDTO;
import com.bluecollar.management.service.CustomerProfileService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;

    public CustomerProfileController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    // ✅ SAFE JWT USER ID EXTRACTION
    private Long getUserId(HttpServletRequest request) {
        Object userIdAttr = request.getAttribute("userId");
        if (userIdAttr == null) {
            throw new RuntimeException("UserId not found in JWT");
        }
        return ((Number) userIdAttr).longValue();
    }

    @GetMapping("/profile")
    public CustomerProfileResponseDTO getProfile(HttpServletRequest request) {
        return customerProfileService.getProfile(getUserId(request));
    }

    @PostMapping(
        value = "/profile",
        consumes = "application/json",
        produces = "application/json"
    )
    public CustomerProfileResponseDTO createProfile(
            HttpServletRequest request,
            @RequestBody CustomerProfileRequestDTO dto) {

        return customerProfileService.createProfile(getUserId(request), dto);
    }

    @PutMapping(
        value = "/profile",
        consumes = "application/json",
        produces = "application/json"
    )
    public CustomerProfileResponseDTO updateProfile(
            HttpServletRequest request,
            @RequestBody CustomerProfileRequestDTO dto) {

        return customerProfileService.updateProfile(getUserId(request), dto);
    }

    @DeleteMapping("/profile")
    public String deleteProfile(HttpServletRequest request) {
        customerProfileService.deleteProfile(getUserId(request));
        return "Customer profile deleted successfully";
    }
}