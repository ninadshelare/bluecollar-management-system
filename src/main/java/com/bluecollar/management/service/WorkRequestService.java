package com.bluecollar.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bluecollar.management.dto.*;
import com.bluecollar.management.entity.*;
import com.bluecollar.management.entity.enums.*;
import com.bluecollar.management.repository.*;

@Service
public class WorkRequestService {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final WorkRequestRepository workRequestRepository;
    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final FeedbackRepository feedbackRepository;

    public WorkRequestService(
            UserRepository userRepository,
            WorkerRepository workerRepository,
            WorkRequestRepository workRequestRepository,
            PaymentRepository paymentRepository,
            CustomerRepository customerRepository,
            FeedbackRepository feedbackRepository) {

        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.workRequestRepository = workRequestRepository;
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.feedbackRepository = feedbackRepository;
    }

    // ================= CREATE =================
    public WorkRequestResponseDTO createWorkRequest(Long userId, Long workerId) {
    	
    	System.out.println("JWT userId = " + userId);
    	System.out.println("WorkerId = " + workerId);

    	User user = userRepository.findById(userId)
    	        .orElseThrow(() -> {
    	            System.out.println("❌ USER NOT FOUND");
    	            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    	        });

        // ✅ THIS IS THE KEY FIX
    	Customer customer = customerRepository.findByUser(user)
    	        .orElseThrow(() -> {
    	            System.out.println("❌ CUSTOMER PROFILE NOT FOUND FOR USER ID " + userId);
    	            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer profile not found");
    	        });

    	Worker worker = workerRepository.findById(workerId)
    	        .orElseThrow(() -> {
    	            System.out.println("❌ WORKER NOT FOUND: " + workerId);
    	            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found");
    	        });

        WorkRequest request = new WorkRequest();
        request.setCustomer(customer); // ✅ CORRECT ENTITY
        request.setWorker(worker);
        request.setServiceCategory(worker.getServiceCategory());
        request.setStatus(WorkRequestStatus.PENDING);
        request.setRequestedAt(LocalDateTime.now());

        return mapToWorkRequestDTO(workRequestRepository.save(request));
    }
    // ================= ACCEPT =================
    public WorkRequestResponseDTO acceptWorkRequest(Long requestId, Long workerId) {

        WorkRequest request = workRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Work request not found"));

        // ✅ Idempotent
        if (request.getStatus() == WorkRequestStatus.ACCEPTED) {
            return mapToWorkRequestDTO(request);
        }

        if (request.getStatus() != WorkRequestStatus.PENDING) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Request already " + request.getStatus());
        }

        if (!request.getWorker().getId().equals(workerId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Worker not assigned to this request");
        }

        Worker worker = request.getWorker();

        if (!Boolean.TRUE.equals(worker.getAvailable())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worker not available");
        }

        request.setStatus(WorkRequestStatus.ACCEPTED);
        worker.setAvailable(false);

        workerRepository.save(worker);
        workRequestRepository.save(request);

        return mapToWorkRequestDTO(request);
    }

    // ================= COMPLETE =================
    @Transactional
    public PaymentResponseDTO completeWorkRequest(Long requestId, Double hoursWorked) {

        WorkRequest request = workRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Work request not found"));

        if (request.getStatus() != WorkRequestStatus.ACCEPTED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Only ACCEPTED requests can be completed");
        }

        Worker worker = request.getWorker();

        WorkerPricing pricing = worker.getPricingList()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pricing not configured"));

        double amount;

        if (pricing.getPricingType() == PricingType.HOURLY) {
            if (hoursWorked == null || hoursWorked <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Hours required for HOURLY pricing");
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

    // ================= CUSTOMER REQUESTS =================
    @Transactional(readOnly = true)
    public List<CustomerWorkRequestResponseDTO> getRequestsForCustomer(Long userId) {

        User customerUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Customer customer = customerRepository.findByUser(customerUser)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Customer profile not completed"
                        ));

        return workRequestRepository.findByCustomer(customer)
                .stream()
                .map(this::mapToCustomerRequestDTO)
                .collect(Collectors.toList());
    }
    // ================= WORKER JOBS =================
    @Transactional(readOnly = true)
    public List<WorkRequestResponseDTO> getRequestsForWorker(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));

        return workRequestRepository.findByWorker(worker)
                .stream()
                .map(this::mapToWorkRequestDTO)
                .collect(Collectors.toList());
    }

    // ================= MAPPERS =================

    private WorkRequestResponseDTO mapToWorkRequestDTO(WorkRequest request) {

        Customer customer = request.getCustomer();

        CustomerSummaryDTO customerDTO = new CustomerSummaryDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getUser().getName());

        customerDTO.setPhone(customer.getPhone());
        customerDTO.setAddressLine1(customer.getAddressLine1());
        customerDTO.setAddressLine2(customer.getAddressLine2());
        customerDTO.setCity(customer.getCity());
        customerDTO.setState(customer.getState());
        customerDTO.setPincode(customer.getPincode());

        Worker worker = request.getWorker();

        WorkerSummaryDTO workerDTO = new WorkerSummaryDTO();
        workerDTO.setId(worker.getId());
        workerDTO.setName(worker.getUser().getName());
        workerDTO.setRating(worker.getRating());
        workerDTO.setAvailable(worker.getAvailable());

        WorkRequestResponseDTO dto = new WorkRequestResponseDTO();
        dto.setRequestId(request.getId());
        dto.setStatus(request.getStatus().name());
        dto.setServiceName(
            request.getServiceCategory() != null
                ? request.getServiceCategory().getName()
                : "UNKNOWN"
        );
        dto.setRequestedAt(request.getRequestedAt());
        dto.setCustomer(customerDTO);
        dto.setWorker(workerDTO);

        return dto;
    }

    private CustomerWorkRequestResponseDTO mapToCustomerRequestDTO(WorkRequest request) {

        CustomerWorkRequestResponseDTO dto = new CustomerWorkRequestResponseDTO();
        dto.setRequestId(request.getId());
        dto.setStatus(request.getStatus().name());
        dto.setRequestedAt(request.getRequestedAt());

        dto.setServiceName(
                request.getServiceCategory() != null
                        ? request.getServiceCategory().getName()
                        : "UNKNOWN"
        );

        paymentRepository.findByWorkRequestId(request.getId())
                .ifPresent(p -> {
                    PaymentSummaryDTO ps = new PaymentSummaryDTO();
                    ps.setPaymentId(p.getId());
                    ps.setAmount(p.getAmount());
                    ps.setStatus(p.getStatus());
                    ps.setPricingType(p.getPricingType());
                    dto.setPayment(ps);
                });

        feedbackRepository.findByWorkRequest(request)
                .ifPresent(f -> {
                    FeedbackSummaryDTO fs = new FeedbackSummaryDTO();
                    fs.setRating(f.getRating());
                    fs.setComment(f.getComment());
                    dto.setFeedback(fs);
                });

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
}