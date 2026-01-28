package com.bluecollar.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecollar.management.entity.WorkerPricing;
import com.bluecollar.management.entity.enums.PricingType;

public interface WorkerPricingRepository
        extends JpaRepository<WorkerPricing, Long> {

    List<WorkerPricing> findByPricingType(PricingType pricingType);

    List<WorkerPricing> findByPricingTypeAndPriceLessThanEqual(
            PricingType pricingType,
            Double price
    );
}
