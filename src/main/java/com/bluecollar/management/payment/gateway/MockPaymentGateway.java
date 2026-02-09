package com.bluecollar.management.payment.gateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bluecollar.management.dto.*;
import com.bluecollar.management.dto.PaymentInitResponse;


@Component
@Profile("dev")
public class MockPaymentGateway implements PaymentGateway {

    private final Map<String, String> otpStore = new HashMap<>();

    @Override
    public PaymentInitResponse initiate(Long paymentId, Double amount) {

        String sessionId = "PAY_" + paymentId + "_" + System.currentTimeMillis();
        String otp = "123456"; // fixed demo OTP

        otpStore.put(sessionId, otp);

        return PaymentInitResponse.builder()
                .paymentSessionId(sessionId)
                .message("OTP sent to registered mobile number")
                .maskedTarget("******7890")
                .build();
    }

    @Override
    public PaymentVerifyResponse verifyOtp(OtpVerifyRequest request) {

        String storedOtp = otpStore.get(request.getPaymentSessionId());

        try {
            Thread.sleep(2000); // simulate gateway delay
        } catch (InterruptedException ignored) {}

        if (storedOtp != null && "123456".equals(request.getOtp())) {
            return PaymentVerifyResponse.builder()
                    .transactionId("TXN_" + System.currentTimeMillis())
                    .status("SUCCESS")
                    .build();
        }

        return PaymentVerifyResponse.builder()
                .status("FAILED")
                .build();
    }
}
