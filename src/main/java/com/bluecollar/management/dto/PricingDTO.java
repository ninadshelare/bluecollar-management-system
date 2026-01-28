package com.bluecollar.management.dto;

import com.bluecollar.management.entity.enums.PricingType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PricingDTO {

    private PricingType pricingType;
    private Double price;

    // getters & setters
}
