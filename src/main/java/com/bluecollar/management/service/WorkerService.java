package com.bluecollar.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.repository.WorkerRepository;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public List<Worker> searchWorkers(
            String service,
            PricingType pricingType,
            Double maxPrice,
            Boolean available,
            Double minRating
    ) {

        // ✅ Defensive defaults (VERY IMPORTANT)
        if (available == null) {
            available = true;
        }

        if (minRating == null) {
            minRating = 0.0;
        }

        return workerRepository.searchWorkers(
                service,
                available,
                pricingType,
                maxPrice,
                minRating
        );
    }
}
