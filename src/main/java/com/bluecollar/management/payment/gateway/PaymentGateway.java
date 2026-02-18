package com.bluecollar.management.payment.gateway;

import com.bluecollar.management.dto.*;

public interface PaymentGateway {

    PaymentInitResponse initiate(Long paymentId, Double amount);

    PaymentVerifyResponse verifyOtp(OtpVerifyRequest request);
}
