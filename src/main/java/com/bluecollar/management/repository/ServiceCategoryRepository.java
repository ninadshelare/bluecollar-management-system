package com.bluecollar.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecollar.management.entity.ServiceCategory;

public interface ServiceCategoryRepository
        extends JpaRepository<ServiceCategory, Long> {

    Optional<ServiceCategory> findByName(String name);
}
