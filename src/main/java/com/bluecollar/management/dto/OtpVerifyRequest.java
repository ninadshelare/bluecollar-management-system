package com.bluecollar.management.dto;

import lombok.Data;

@Data
public class OtpVerifyRequest {
    private String paymentSessionId;
    private String otp;
}
