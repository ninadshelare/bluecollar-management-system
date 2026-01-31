package com.bluecollar.management.controller;

import java.util.List;

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
    public WorkerSearchResponseDTO createOrUpdateProfile(
            @RequestParam Long userId, // TEMP (JWT later)
            @RequestBody WorkerProfileRequestDTO request) {

        return workerProfileService.createOrUpdateProfile(userId, request);
    }
}


