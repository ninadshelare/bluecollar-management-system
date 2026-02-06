package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.WorkerEarningsResponseDTO;
import com.bluecollar.management.service.WorkerEarningsService;

@RestController
@RequestMapping("/api/workers")
public class WorkerEarningsController {

    private final WorkerEarningsService workerEarningsService;

    public WorkerEarningsController(WorkerEarningsService workerEarningsService) {
        this.workerEarningsService = workerEarningsService;
    }

    @GetMapping("/{workerId}/earnings")
    public WorkerEarningsResponseDTO getEarnings(
            @PathVariable Long workerId) {

        return workerEarningsService.getWorkerEarnings(workerId);
    }
}
