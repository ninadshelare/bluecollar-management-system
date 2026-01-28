package com.bluecollar.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.WorkerPricing;
import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.repository.WorkerPricingRepository;

@Service
public class WorkerPricingSearchService {

    private final WorkerPricingRepository pricingRepository;

    public WorkerPricingSearchService(WorkerPricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    public List<Worker> getWorkersByPricingType(PricingType pricingType) {

        return pricingRepository
                .findByPricingType(pricingType)
                .stream()
                .map(WorkerPricing::getWorker)
                .collect(Collectors.toList());
    }

    public List<Worker> getWorkersByPricingTypeAndMaxPrice(
            PricingType pricingType,
            Double maxPrice) {

        return pricingRepository
                .findByPricingTypeAndPriceLessThanEqual(pricingType, maxPrice)
                .stream()
                .map(WorkerPricing::getWorker)
                .collect(Collectors.toList());
    }
}
