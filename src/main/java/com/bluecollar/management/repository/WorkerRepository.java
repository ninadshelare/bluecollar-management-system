package com.bluecollar.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bluecollar.management.entity.ServiceCategory;
import com.bluecollar.management.entity.User;
import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.enums.PricingType;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByUserAndActiveTrue(User user);


    @Query("""
            SELECT DISTINCT w
            FROM Worker w
            JOIN w.user u
            JOIN w.serviceCategory sc
            LEFT JOIN FETCH w.pricingList p
            WHERE u.role = com.bluecollar.management.entity.enums.Role.WORKER
              AND w.active = true
              AND sc.name = :service
              AND w.available = :available
              AND (:pricingType IS NULL OR p.pricingType = :pricingType)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
              AND (:minRating IS NULL OR w.rating >= :minRating)
        """)
        List<Worker> searchWorkers(
                @Param("service") String service,
                @Param("available") Boolean available,
                @Param("pricingType") PricingType pricingType,
                @Param("maxPrice") Double maxPrice,
                @Param("minRating") Double minRating
        );


    // legacy / optional
    List<Worker> findByServiceCategory(ServiceCategory serviceCategory);

    List<Worker> findByServiceCategoryAndAvailableTrue(ServiceCategory serviceCategory);
}
