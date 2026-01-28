package com.bluecollar.management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.WorkerSearchResponseDTO;
import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.service.WorkerSearchService;

@RestController
@RequestMapping("/api/workers")
public class WorkerSearchController {

    private final WorkerSearchService workerSearchService;

    public WorkerSearchController(WorkerSearchService workerSearchService) {
        this.workerSearchService = workerSearchService;
    }

    @GetMapping("/search")
    public List<WorkerSearchResponseDTO> searchWorkers(
            @RequestParam String service,
            @RequestParam PricingType pricingType,
            @RequestParam Double maxPrice,
            @RequestParam Double minRating
    ) {
        return workerSearchService.searchWorkers(
                service,
                pricingType,
                maxPrice,
                minRating
        );
    }
}
