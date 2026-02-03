package com.bluecollar.management.dto;

import com.bluecollar.management.entity.enums.PaymentStatus;
import com.bluecollar.management.entity.enums.PricingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentSummaryDTO {

    private Long paymentId;
    private Double amount;
    private PaymentStatus status;
    private PricingType pricingType;

}

