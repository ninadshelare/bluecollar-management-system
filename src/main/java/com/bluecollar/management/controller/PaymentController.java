package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.PaymentResponseDTO;
import com.bluecollar.management.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 🔹 Get payment for a work request
    @GetMapping("/by-request/{workRequestId}")
    public PaymentResponseDTO getPaymentByRequest(
            @PathVariable Long workRequestId) {

        return paymentService.getPaymentByWorkRequest(workRequestId);
    }

    // 🔹 Mark payment as PAID
    @PostMapping("/{paymentId}/pay")
    public PaymentResponseDTO pay(
            @PathVariable Long paymentId) {

        return paymentService.markPaymentAsPaid(paymentId);
    }
}
