package com.bluecollar.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerEarningsResponseDTO {

    private Long workerId;
    private Double totalEarnings;
    private Double paidEarnings;
    private Double pendingEarnings;
    private Integer totalJobs;
}
