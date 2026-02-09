package com.bluecollar.management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInitResponse {
    private String paymentSessionId;
    private String maskedTarget;
    private String message;
}

