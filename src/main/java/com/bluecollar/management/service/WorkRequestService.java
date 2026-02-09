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
    public WorkRequestResponseDTO createWorkRequest(Long customerId, Long workerId) {

        User customerUser = userRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        if (customerUser.getRole() != Role.CUSTOMER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not CUSTOMER");
        }

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));

        WorkRequest request = new WorkRequest();
        request.setCustomer(customerUser);
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

        return workRequestRepository.findByCustomer(customerUser)
                .stream()
                .map(req -> {
                    try {
                        return mapToCustomerRequestDTO(req);
                    } catch (Exception e) {
                        e.printStackTrace(); // prevents total API failure
                        return null;
                    }
                })
                .filter(Objects::nonNull)
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

        CustomerSummaryDTO customerDTO = new CustomerSummaryDTO();
        customerDTO.setId(request.getCustomer().getId());
        customerDTO.setName(request.getCustomer().getName());

        customerRepository.findByUserId(request.getCustomer().getId())
                .ifPresent(c -> {
                    customerDTO.setPhone(c.getPhone());
                    customerDTO.setAddressLine1(c.getAddressLine1());
                    customerDTO.setAddressLine2(c.getAddressLine2());
                    customerDTO.setCity(c.getCity());
                    customerDTO.setState(c.getState());
                    customerDTO.setPincode(c.getPincode());
                });

        WorkerSummaryDTO workerDTO = new WorkerSummaryDTO();
        workerDTO.setId(request.getWorker().getId());
        workerDTO.setName(request.getWorker().getUser().getName());
        workerDTO.setRating(request.getWorker().getRating());
        workerDTO.setAvailable(request.getWorker().getAvailable());

        WorkRequestResponseDTO dto = new WorkRequestResponseDTO();
        dto.setRequestId(request.getId());
        dto.setStatus(request.getStatus().name());

        // ✅ NULL SAFE
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