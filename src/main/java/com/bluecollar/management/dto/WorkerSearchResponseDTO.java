package com.bluecollar.management.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WorkerSearchResponseDTO {

    private Long workerId;
    private String name;
    private String service;
    private Double rating;
    private Integer experienceYears;
    private Boolean available;
    private List<PricingDTO> pricing;

    // getters & setters
}
