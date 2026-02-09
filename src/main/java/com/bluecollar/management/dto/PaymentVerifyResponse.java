package com.bluecollar.management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentVerifyResponse {
    private String transactionId;
    private String status; // SUCCESS / FAILED
}
