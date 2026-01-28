package com.bluecollar.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bluecollar.management.dto.PricingDTO;
import com.bluecollar.management.dto.WorkerSearchResponseDTO;
import com.bluecollar.management.entity.Worker;
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

        List<PricingDTO> pricingDTOs = worker.getPricingList()
                .stream()
                .map(p -> {
                    PricingDTO pd = new PricingDTO();
                    pd.setPricingType(p.getPricingType());
                    pd.setPrice(p.getPrice());
                    return pd;
                })
                .collect(Collectors.toList());

        dto.setPricing(pricingDTOs);
        return dto;
    }
}
