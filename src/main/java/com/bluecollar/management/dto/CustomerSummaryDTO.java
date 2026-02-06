package com.bluecollar.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSummaryDTO {

    private Long id;
    private String name;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;
}

