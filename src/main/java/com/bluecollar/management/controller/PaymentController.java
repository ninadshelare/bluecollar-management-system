package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.*;
import com.bluecollar.management.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ✅ EXISTING – unchanged
    @GetMapping("/by-request/{workRequestId}")
    public PaymentResponseDTO getPaymentByRequest(
            @PathVariable Long workRequestId) {

        return paymentService.getPaymentByWorkRequest(workRequestId);
    }

    // 🆕 STEP 1: Initiate payment (send OTP)
    @PostMapping("/{paymentId}/initiate")
    public PaymentInitResponse initiatePayment(
            @PathVariable Long paymentId) {

        return paymentService.initiatePayment(paymentId);
    }

    // 🆕 STEP 2: Verify OTP & complete payment
    @PostMapping("/{paymentId}/verify-otp")
    public PaymentResponseDTO verifyOtpAndPay(
            @PathVariable Long paymentId,
            @RequestBody OtpVerifyRequest request) {

        return paymentService.verifyOtpAndCompletePayment(paymentId, request);
    }
}
