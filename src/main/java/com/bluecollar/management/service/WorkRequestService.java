package com.bluecollar.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluecollar.management.dto.CustomerSummaryDTO;
import com.bluecollar.management.dto.CustomerWorkRequestResponseDTO;
import com.bluecollar.management.dto.PaymentResponseDTO;
import com.bluecollar.management.dto.PaymentSummaryDTO;
import com.bluecollar.management.dto.WorkRequestResponseDTO;
import com.bluecollar.management.dto.WorkerSummaryDTO;
import com.bluecollar.management.entity.Payment;
import com.bluecollar.management.entity.User;
import com.bluecollar.management.entity.WorkRequest;
import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.WorkerPricing;
import com.bluecollar.management.entity.enums.PaymentStatus;
import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.entity.enums.Role;
import com.bluecollar.management.entity.enums.WorkRequestStatus;
import com.bluecollar.management.repository.PaymentRepository;
import com.bluecollar.management.repository.UserRepository;
import com.bluecollar.management.repository.WorkRequestRepository;
import com.bluecollar.management.repository.WorkerRepository;

@Service
public class WorkRequestService {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final WorkRequestRepository workRequestRepository;
    private final PaymentRepository paymentRepository;

    public WorkRequestService(
            UserRepository userRepository,
            WorkerRepository workerRepository,
            WorkRequestRepository workRequestRepository,
            PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.workRequestRepository = workRequestRepository;
        this.paymentRepository = paymentRepository;
    }

    // ================= CREATE =================
    public WorkRequestResponseDTO createWorkRequest(Long customerId, Long workerId) {

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("User is not a CUSTOMER");
        }

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        WorkRequest request = new WorkRequest();
        request.setCustomer(customer);
        request.setWorker(worker);
        request.setServiceCategory(worker.getServiceCategory());
        request.setStatus(WorkRequestStatus.PENDING);
        request.setRequestedAt(LocalDateTime.now());

        return mapToWorkRequestDTO(workRequestRepository.save(request));
    }

    // ================= ACCEPT =================
    public WorkRequestResponseDTO acceptWorkRequest(Long requestId, Long workerId) {

        WorkRequest request = workRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Work request not found"));

        if (request.getStatus() != WorkRequestStatus.PENDING) {
            throw new RuntimeException("Only PENDING requests can be accepted");
        }

        if (!request.getWorker().getId().equals(workerId)) {
            throw new RuntimeException("This worker is not assigned to the request");
        }

        Worker worker = request.getWorker();

        if (!worker.getAvailable()) {
            throw new RuntimeException("Worker is not available");
        }

        request.setStatus(WorkRequestStatus.ACCEPTED);
        worker.setAvailable(false);

        workerRepository.save(worker);
        return mapToWorkRequestDTO(workRequestRepository.save(request));
    }

    // ================= COMPLETE =================
    @Transactional
    public PaymentResponseDTO completeWorkRequest(Long requestId, Double hoursWorked) {

        WorkRequest request = workRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Work request not found"));

        if (request.getStatus() != WorkRequestStatus.ACCEPTED) {
            throw new RuntimeException("Only ACCEPTED requests can be completed");
        }

        Worker worker = request.getWorker();

        WorkerPricing pricing = worker.getPricingList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pricing not configured"));

        double amount;

        if (pricing.getPricingType() == PricingType.HOURLY) {
            if (hoursWorked == null || hoursWorked <= 0) {
                throw new RuntimeException("Hours worked required for HOURLY pricing");
            }
            amount = pricing.getPrice() * hoursWorked;
        } else {
            amount = pricing.getPrice();
        }

        request.setStatus(WorkRequestStatus.COMPLETED);
        worker.setAvailable(true);

        Payment payment = new Payment();
        payment.setWorker(worker);
        payment.setWorkRequest(request);
        payment.setPricingType(pricing.getPricingType());
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        workerRepository.save(worker);
        workRequestRepository.save(request);
        paymentRepository.save(payment);

        return mapToPaymentDTO(payment);
    }

    // ================= CUSTOMER: MY REQUESTS (WITH PAYMENT) =================
    public List<CustomerWorkRequestResponseDTO> getRequestsForCustomer(Long userId) {

        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("User is not CUSTOMER");
        }

        return workRequestRepository.findByCustomer(customer)
                .stream()
                .map(this::mapToCustomerRequestDTO)
                .collect(Collectors.toList());
    }

    // ================= WORKER: MY JOBS =================
    public List<WorkRequestResponseDTO> getRequestsForWorker(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        return workRequestRepository.findByWorker(worker)
                .stream()
                .map(this::mapToWorkRequestDTO)
                .collect(Collectors.toList());
    }

    // ================= MAPPERS =================
    private WorkRequestResponseDTO mapToWorkRequestDTO(WorkRequest request) {

        CustomerSummaryDTO customerDTO = new CustomerSummaryDTO();
        customerDTO.setId(request.getCustomer().getId());
        customerDTO.setName(request.getCustomer().getName());

        WorkerSummaryDTO workerDTO = new WorkerSummaryDTO();
        workerDTO.setId(request.getWorker().getId());
        workerDTO.setName(request.getWorker().getUser().getName());
        workerDTO.setRating(request.getWorker().getRating());
        workerDTO.setAvailable(request.getWorker().getAvailable());

        WorkRequestResponseDTO dto = new WorkRequestResponseDTO();
        dto.setRequestId(request.getId());
        dto.setStatus(request.getStatus().name());
        dto.setServiceName(request.getServiceCategory().getName());
        dto.setRequestedAt(request.getRequestedAt());
        dto.setCustomer(customerDTO);
        dto.setWorker(workerDTO);

        return dto;
    }

    private PaymentResponseDTO mapToPaymentDTO(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getId());
        dto.setWorkRequestId(payment.getWorkRequest().getId());
        dto.setWorkerId(payment.getWorker().getId());
        dto.setPricingType(payment.getPricingType());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setCreatedAt(payment.getCreatedAt());

        return dto;
    }

    private CustomerWorkRequestResponseDTO mapToCustomerRequestDTO(WorkRequest request) {

        CustomerWorkRequestResponseDTO dto = new CustomerWorkRequestResponseDTO();
        dto.setRequestId(request.getId());
        dto.setServiceName(request.getServiceCategory().getName());
        dto.setStatus(request.getStatus().name());
        dto.setRequestedAt(request.getRequestedAt());

        paymentRepository.findByWorkRequestId(request.getId())
                .ifPresent(payment -> {
                    PaymentSummaryDTO p = new PaymentSummaryDTO();
                    p.setPaymentId(payment.getId());
                    p.setAmount(payment.getAmount());
                    p.setStatus(payment.getStatus());
                    p.setPricingType(payment.getPricingType());
                    dto.setPayment(p);
                });

        return dto;
    }
}
