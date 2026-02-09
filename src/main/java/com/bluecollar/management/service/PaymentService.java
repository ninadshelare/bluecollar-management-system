package com.bluecollar.management.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluecollar.management.dto.*;
import com.bluecollar.management.entity.Payment;
import com.bluecollar.management.entity.enums.PaymentStatus;
import com.bluecollar.management.payment.gateway.PaymentGateway;
import com.bluecollar.management.repository.PaymentRepository;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentGateway paymentGateway) {
        this.paymentRepository = paymentRepository;
        this.paymentGateway = paymentGateway;
    }

    // ✅ EXISTING – unchanged
    public PaymentResponseDTO getPaymentByWorkRequest(Long workRequestId) {

        Payment payment = paymentRepository.findByWorkRequestId(workRequestId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return mapToDTO(payment);
    }

    // 🆕 STEP 1: Initiate payment (OTP sent)
    public PaymentInitResponse initiatePayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Payment already completed");
        }

        return paymentGateway.initiate(paymentId, payment.getAmount());
    }

    // 🆕 STEP 2: Verify OTP and mark payment PAID
    @Transactional
    public PaymentResponseDTO verifyOtpAndCompletePayment(
            Long paymentId,
            OtpVerifyRequest request) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        PaymentVerifyResponse response =
                paymentGateway.verifyOtp(request);

        if (!"SUCCESS".equals(response.getStatus())) {
            throw new RuntimeException("Payment failed");
        }

        payment.setStatus(PaymentStatus.PAID);
        payment.setTransactionId(response.getTransactionId());
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return mapToDTO(payment);
    }

    // ✅ EXISTING – unchanged
    private PaymentResponseDTO mapToDTO(Payment payment) {

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
