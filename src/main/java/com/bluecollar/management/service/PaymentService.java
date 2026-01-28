package com.bluecollar.management.service;

import org.springframework.stereotype.Service;

import com.bluecollar.management.dto.PaymentResponseDTO;
import com.bluecollar.management.entity.Payment;
import com.bluecollar.management.entity.enums.PaymentStatus;
import com.bluecollar.management.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponseDTO getPaymentByWorkRequest(Long workRequestId) {

        Payment payment = paymentRepository.findByWorkRequestId(workRequestId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return mapToDTO(payment);
    }

    public PaymentResponseDTO markPaymentAsPaid(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Payment already completed");
        }

        payment.setStatus(PaymentStatus.PAID);

        return mapToDTO(paymentRepository.save(payment));
    }

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
