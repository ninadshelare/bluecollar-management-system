package com.bluecollar.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bluecollar.management.dto.PricingDTO;
import com.bluecollar.management.dto.WorkerSearchResponseDTO;
import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.WorkerPricing;
import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.repository.WorkerRepository;

@Service
public class WorkerSearchService {

    private final WorkerRepository workerRepository;

    public WorkerSearchService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public List<WorkerSearchResponseDTO> searchWorkers(
            String service,
            PricingType pricingType,
            Double maxPrice,
            Double minRating
    ) {
        List<Worker> workers = workerRepository.searchWorkers(
                service,
                true,
                pricingType,
                maxPrice,
                minRating
        );

        return workers.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    private WorkerSearchResponseDTO mapToDTO(Worker worker) {

        WorkerSearchResponseDTO dto = new WorkerSearchResponseDTO();
        dto.setWorkerId(worker.getId());
        dto.setName(worker.getUser().getName());
        dto.setService(worker.getServiceCategory().getName());
        dto.setRating(worker.getRating());
        dto.setExperienceYears(worker.getExperienceYears());
        dto.setAvailable(worker.getAvailable());

        // ✅ SAFE PRICING HANDLING
        if (worker.getPricingList() != null && !worker.getPricingList().isEmpty()) {

            WorkerPricing pricing = worker.getPricingList()
                    .stream()
                    .findFirst()
                    .orElse(null);


            PricingDTO p = new PricingDTO();
            p.setPricingType(pricing.getPricingType());
            p.setPrice(pricing.getPrice());

            dto.setPricing(List.of(p));

        } else {
            // 🔒 DO NOT CRASH SEARCH
            dto.setPricing(List.of());
        }

        return dto;
    }


}
