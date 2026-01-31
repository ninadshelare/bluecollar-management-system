package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.WorkerProfileRequestDTO;
import com.bluecollar.management.dto.WorkerSearchResponseDTO;
import com.bluecollar.management.service.WorkerProfileService;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerProfileService workerProfileService;

    public WorkerController(WorkerProfileService workerProfileService) {
        this.workerProfileService = workerProfileService;
    }

    @PostMapping("/profile")
    public WorkerSearchResponseDTO createProfile(
            @RequestParam Long userId,   // TEMP until JWT
            @RequestBody WorkerProfileRequestDTO request) {

        return workerProfileService.createProfile(userId, request);
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
}
