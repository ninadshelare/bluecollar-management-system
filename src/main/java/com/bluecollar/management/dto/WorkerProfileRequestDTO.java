package com.bluecollar.management.dto;

import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.entity.enums.WorkerType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerProfileRequestDTO {

    private String serviceName;

    private WorkerType workerType;      

    private PricingType pricingType;

    private Double price;

    private Integer experienceYears;
}
