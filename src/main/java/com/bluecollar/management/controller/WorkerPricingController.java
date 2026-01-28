package com.bluecollar.management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.service.WorkerPricingSearchService;

@RestController
@RequestMapping("/workers/pricing")
public class WorkerPricingController {

    private final WorkerPricingSearchService pricingSearchService;

    public WorkerPricingController(WorkerPricingSearchService pricingSearchService) {
        this.pricingSearchService = pricingSearchService;
    }

    @GetMapping("/type/{pricingType}")
    public List<Worker> getWorkersByPricingType(
            @PathVariable PricingType pricingType) {

        return pricingSearchService.getWorkersByPricingType(pricingType);
    }

    @GetMapping("/type/{pricingType}/max/{price}")
    public List<Worker> getWorkersByPricingAndMaxPrice(
            @PathVariable PricingType pricingType,
            @PathVariable Double price) {

        return pricingSearchService
                .getWorkersByPricingTypeAndMaxPrice(pricingType, price);
    }
}
