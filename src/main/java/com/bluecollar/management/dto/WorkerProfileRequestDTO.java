package com.bluecollar.management.dto;

import com.bluecollar.management.entity.enums.PricingType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WorkerProfileRequestDTO {

    private String serviceName;
    private PricingType pricingType;
    private Double price;
    private Integer experienceYears;

    // getters & setters
}
