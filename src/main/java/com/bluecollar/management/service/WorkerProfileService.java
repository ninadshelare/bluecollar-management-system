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

    // ================= CREATE PROFILE =================
    @Transactional
    public WorkerSearchResponseDTO createProfile(
            Long userId,
            WorkerProfileRequestDTO request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.WORKER) {
            throw new RuntimeException("Only WORKER can create profile");
        }

        if (workerRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("Worker profile already exists");
        }

        Worker worker = buildWorkerFromRequest(user, request);
        worker = workerRepository.save(worker);

        WorkerPricing pricing = buildPricing(worker, request);
        workerPricingRepository.save(pricing);

        return mapToSearchDTO(worker, pricing);
    }

    // ================= UPDATE PROFILE (FIXED) =================
    @Transactional
    public WorkerSearchResponseDTO updateProfile(
            Long workerId,
            WorkerProfileRequestDTO request) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        ServiceCategory service = serviceCategoryRepository
                .findByName(request.getServiceName())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // update worker fields
        worker.setServiceCategory(service);
        worker.setExperienceYears(request.getExperienceYears());
        workerRepository.save(worker);

        // ✅ ALWAYS fetch existing pricing from DB
        WorkerPricing pricing = workerPricingRepository
                .findByWorker(worker)
                .orElseThrow(() -> new RuntimeException("Pricing not found"));

        pricing.setPricingType(request.getPricingType());
        pricing.setPrice(request.getPrice());

        workerPricingRepository.save(pricing);

        return mapToSearchDTO(worker, pricing);
    }


    // ================= DELETE PROFILE =================
    @Transactional
    public void deleteProfile(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        workerRepository.delete(worker);
    }

    // ================= GET PROFILE BY USER =================
    public WorkerSearchResponseDTO getProfileByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.WORKER) {
            throw new RuntimeException("User is not a WORKER");
        }

        Worker worker = workerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Worker profile not found"));

        WorkerPricing pricing = worker.getPricingList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pricing not found"));

        return mapToSearchDTO(worker, pricing);
    }

    // ================= HELPER METHODS =================

    private Worker buildWorkerFromRequest(
            User user,
            WorkerProfileRequestDTO request) {

        ServiceCategory service = serviceCategoryRepository
                .findByName(request.getServiceName())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Worker worker = new Worker();
        worker.setUser(user);
        worker.setServiceCategory(service);
        worker.setExperienceYears(request.getExperienceYears());
        worker.setAvailable(true);
        worker.setRating(0.0);

        return worker;
    }

    private WorkerPricing buildPricing(
            Worker worker,
            WorkerProfileRequestDTO request) {

        WorkerPricing pricing = new WorkerPricing();
        pricing.setWorker(worker);
        pricing.setPricingType(request.getPricingType());
        pricing.setPrice(request.getPrice());
        return pricing;
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
