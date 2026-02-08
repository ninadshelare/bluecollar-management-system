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
import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.entity.enums.Role;
import com.bluecollar.management.entity.enums.WorkerType;
import com.bluecollar.management.repository.ServiceCategoryRepository;
import com.bluecollar.management.repository.UserRepository;
import com.bluecollar.management.repository.WorkerRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkerProfileService {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;

    public WorkerProfileService(
            UserRepository userRepository,
            WorkerRepository workerRepository,
            ServiceCategoryRepository serviceCategoryRepository) {
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    // ================= CREATE PROFILE =================
    @Transactional
    public WorkerSearchResponseDTO createProfile(Long userId, WorkerProfileRequestDTO request) {

        if (request.getWorkerType() == null)
            throw new RuntimeException("workerType is required");

        if (request.getPricingType() == null)
            throw new RuntimeException("pricingType is required");

        if (request.getServiceName() == null)
            throw new RuntimeException("serviceName is required");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.WORKER)
            throw new RuntimeException("Only WORKER can create profile");

        if (workerRepository.findByUserAndActiveTrue(user).isPresent())
            throw new RuntimeException("Worker profile already exists");


        validatePricing(request.getWorkerType(), request.getPricingType());

        ServiceCategory service = serviceCategoryRepository
                .findByName(request.getServiceName().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Worker worker = new Worker();
        worker.setUser(user);
        worker.setServiceCategory(service);
        worker.setWorkerType(request.getWorkerType());
        worker.setExperienceYears(request.getExperienceYears());
        worker.setAvailable(true);
        worker.setRating(0.0);
        worker.setActive(true);

        WorkerPricing pricing = new WorkerPricing();
        pricing.setWorker(worker);
        pricing.setPricingType(request.getPricingType());
        pricing.setPrice(request.getPrice());

        // 🔥 CRITICAL FIX: attach pricing to worker
        worker.getPricingList().add(pricing);

        worker = workerRepository.save(worker);

        return mapToSearchDTO(worker, pricing);
    }

    // ================= UPDATE PROFILE =================
    @Transactional
    public WorkerSearchResponseDTO updateProfile(Long workerId, WorkerProfileRequestDTO request) {

        if (request.getWorkerType() == null)
            throw new RuntimeException("workerType is required");

        if (request.getPricingType() == null)
            throw new RuntimeException("pricingType is required");

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (!Boolean.TRUE.equals(worker.getActive()))
            throw new RuntimeException("Worker profile is deleted");

        validatePricing(request.getWorkerType(), request.getPricingType());

        ServiceCategory service = serviceCategoryRepository
                .findByName(request.getServiceName().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        worker.setServiceCategory(service);
        worker.setWorkerType(request.getWorkerType());
        worker.setExperienceYears(request.getExperienceYears());

        WorkerPricing pricing;
        if (worker.getPricingList().isEmpty()) {
            pricing = new WorkerPricing();
            pricing.setWorker(worker);
            worker.getPricingList().add(pricing);
        } else {
            pricing = worker.getPricingList().get(0);
        }

        pricing.setPricingType(request.getPricingType());
        pricing.setPrice(request.getPrice());

        workerRepository.save(worker);

        return mapToSearchDTO(worker, pricing);
    }

    // ================= DELETE PROFILE (SOFT DELETE) =================
    @Transactional
    public void deleteProfile(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        worker.setActive(false);
        worker.setAvailable(false);

        workerRepository.save(worker);
    }

    // ================= GET PROFILE BY USER =================
    public WorkerSearchResponseDTO getProfileByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.WORKER)
            throw new RuntimeException("User is not a WORKER");

        Worker worker = workerRepository.findByUserAndActiveTrue(user)
                .filter(Worker::getActive) // 🔥 IMPORTANT
                .orElseThrow(() -> new RuntimeException("Worker profile not found"));

        WorkerPricing pricing = worker.getPricingList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pricing not found"));

        return mapToSearchDTO(worker, pricing);
    }

    // ================= VALIDATION =================
    private void validatePricing(WorkerType workerType, PricingType pricingType) {

        if (workerType == WorkerType.SKILLED && pricingType != PricingType.PER_JOB)
            throw new RuntimeException("SKILLED workers must have PER_JOB pricing");

        if (workerType == WorkerType.LABOUR && pricingType != PricingType.HOURLY)
            throw new RuntimeException("LABOUR workers must have HOURLY pricing");

        if (workerType == WorkerType.MAID && pricingType != PricingType.MONTHLY)
            throw new RuntimeException("MAID workers must have MONTHLY pricing");
    }

    // ================= DTO MAPPER =================
    private WorkerSearchResponseDTO mapToSearchDTO(Worker worker, WorkerPricing pricing) {

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
