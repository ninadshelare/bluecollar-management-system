package com.bluecollar.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.ServiceCategory;
import com.bluecollar.management.entity.enums.PricingType;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    
    @Query("""
        SELECT DISTINCT w
        FROM Worker w
        JOIN w.serviceCategory sc
        JOIN w.pricingList p
        WHERE sc.name = :service
          AND w.available = :available
          AND p.pricingType = :pricingType
          AND p.price <= :maxPrice
          AND w.rating >= :minRating
    """)
    List<Worker> searchWorkers(
            @Param("service") String service,
            @Param("available") Boolean available,
            @Param("pricingType") PricingType pricingType,
            @Param("maxPrice") Double maxPrice,
            @Param("minRating") Double minRating
    );

    
    List<Worker> findByServiceCategory(ServiceCategory serviceCategory);

    List<Worker> findByServiceCategoryAndAvailableTrue(ServiceCategory serviceCategory);
}
