package com.bluecollar.management.dto;

import java.time.LocalDateTime;

import com.bluecollar.management.entity.enums.PaymentStatus;
import com.bluecollar.management.entity.enums.PricingType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDTO {

    private Long paymentId;

    private Long workRequestId;

    private Long workerId;

    private PricingType pricingType;

    private Double amount;

    private PaymentStatus status;

    private LocalDateTime createdAt;
}
