package com.bluecollar.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluecollar.management.dto.PricingDTO;
import com.bluecollar.management.dto.WorkerProfileRequestDTO;
import com.bluecollar.management.dto.WorkerSearchResponseDTO;
import com.bluecollar.management.entity.ServiceCategory;
import com.bluecollar.management.entity.User;
import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.WorkerPricing;
import com.bluecollar.management.entity.enums.Role;
import com.bluecollar.management.repository.ServiceCategoryRepository;
import com.bluecollar.management.repository.UserRepository;
import com.bluecollar.management.repository.WorkerPricingRepository;
import com.bluecollar.management.repository.WorkerRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkerProfileService {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final WorkerPricingRepository workerPricingRepository;

    public WorkerProfileService(
            UserRepository userRepository,
            WorkerRepository workerRepository,
            ServiceCategoryRepository serviceCategoryRepository,
            WorkerPricingRepository workerPricingRepository) {
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.workerPricingRepository = workerPricingRepository;
    }

    @Transactional
    public WorkerSearchResponseDTO createOrUpdateProfile(
            Long userId,
            WorkerProfileRequestDTO request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.WORKER) {
            throw new RuntimeException("Only WORKER can create profile");
        }

        ServiceCategory service = serviceCategoryRepository
                .findByName(request.getServiceName())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Worker worker = workerRepository.findByUser(user)
                .orElse(new Worker());

        worker.setUser(user);
        worker.setServiceCategory(service);
        worker.setExperienceYears(request.getExperienceYears());
        worker.setAvailable(true);

        worker = workerRepository.save(worker);

        workerPricingRepository.deleteByWorker(worker);

        WorkerPricing pricing = new WorkerPricing();
        pricing.setWorker(worker);
        pricing.setPricingType(request.getPricingType());
        pricing.setPrice(request.getPrice());

        workerPricingRepository.save(pricing);

        return mapToSearchDTO(worker, pricing);
    }

    private WorkerSearchResponseDTO mapToSearchDTO(
            Worker worker,
            WorkerPricing pricing) {

        WorkerSearchResponseDTO dto = new WorkerSearchResponseDTO();
        dto.setWorkerId(worker.getId());
        dto.setName(worker.getUser().getName());
        dto.setService(worker.getServiceCategory().getName());
        dto.setRating(worker.getRating());
        dto.setExperienceYears(worker.getExperienceYears());
        dto.setAvailable(worker.getAvailable());

        PricingDTO p = new PricingDTO();
        p.setPricingType(pricing.getPricingType());
        p.setPrice(pricing.getPrice());

        dto.setPricing(List.of(p));
        return dto;
    }
}
