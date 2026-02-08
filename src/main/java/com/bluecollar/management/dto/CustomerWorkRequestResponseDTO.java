package com.bluecollar.management.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerWorkRequestResponseDTO {

    private Long requestId;
    private String serviceName;
    private String status;
    private LocalDateTime requestedAt;
    private PaymentSummaryDTO payment; // can be NULL

}

