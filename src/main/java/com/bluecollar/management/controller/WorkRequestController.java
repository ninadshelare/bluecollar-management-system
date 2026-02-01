package com.bluecollar.management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.PaymentResponseDTO;
import com.bluecollar.management.dto.WorkRequestResponseDTO;
import com.bluecollar.management.dto.CustomerWorkRequestResponseDTO;
import com.bluecollar.management.service.WorkRequestService;

@RestController
@RequestMapping("/api/work-requests")
public class WorkRequestController {

    private final WorkRequestService workRequestService;

    public WorkRequestController(WorkRequestService workRequestService) {
        this.workRequestService = workRequestService;
    }

    @PostMapping("/create")
    public WorkRequestResponseDTO createRequest(
            @RequestParam Long customerId,
            @RequestParam Long workerId) {

        return workRequestService.createWorkRequest(customerId, workerId);
    }

    @PostMapping("/accept")
    public WorkRequestResponseDTO acceptRequest(
            @RequestParam Long requestId,
            @RequestParam Long workerId) {

        return workRequestService.acceptWorkRequest(requestId, workerId);
    }

    @PostMapping("/complete")
    public PaymentResponseDTO completeRequest(
            @RequestParam Long requestId,
            @RequestParam(required = false) Double hoursWorked) {

        return workRequestService.completeWorkRequest(requestId, hoursWorked);
    }

    @GetMapping("/worker/{workerId}")
    public List<WorkRequestResponseDTO> getWorkerRequests(
            @PathVariable Long workerId) {

        return workRequestService.getRequestsForWorker(workerId);
    }

    @GetMapping("/customer/{userId}")
    public List<CustomerWorkRequestResponseDTO> getCustomerRequests(
            @PathVariable Long userId) {

        return workRequestService.getRequestsForCustomer(userId);
    }
}
