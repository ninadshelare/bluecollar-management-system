package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.WorkerProfileRequestDTO;
import com.bluecollar.management.dto.WorkerSearchResponseDTO;
import com.bluecollar.management.service.WorkerProfileService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerProfileService workerProfileService;

    public WorkerController(WorkerProfileService workerProfileService) {
        this.workerProfileService = workerProfileService;
    }

    @PostMapping("/profile")
    public WorkerSearchResponseDTO createProfile(
            HttpServletRequest request,
            @RequestBody WorkerProfileRequestDTO dto) {

        Long userId = ((Number) request.getAttribute("userId")).longValue();
        return workerProfileService.createProfile(userId, dto);
    }

    @PutMapping("/profile/{workerId}")
    public WorkerSearchResponseDTO updateProfile(
            @PathVariable Long workerId,
            @RequestBody WorkerProfileRequestDTO request) {

        return workerProfileService.updateProfile(workerId, request);
    }

    @DeleteMapping("/profile/{workerId}")
    public String deleteProfile(@PathVariable Long workerId) {

        workerProfileService.deleteProfile(workerId);
        return "Worker profile deleted successfully";
    }
    
    @GetMapping("/profile/by-user/{userId}")
    public WorkerSearchResponseDTO getProfileByUser(
            @PathVariable Long userId) {

        return workerProfileService.getProfileByUserId(userId);
    }

}
