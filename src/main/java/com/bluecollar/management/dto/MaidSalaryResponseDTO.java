package com.bluecollar.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaidSalaryResponseDTO {

    private Long salaryId;
    private Long workerId;
    private String month;
    private Integer presentDays;
    private Double monthlyPrice;
    private Double amount;
    private Boolean paid;
}
