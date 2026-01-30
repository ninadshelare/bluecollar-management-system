package com.bluecollar.management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.entity.ServiceCategory;
import com.bluecollar.management.repository.ServiceCategoryRepository;

@RestController
@RequestMapping("/api/services")
public class ServiceCategoryController {

    private final ServiceCategoryRepository serviceCategoryRepository;

    public ServiceCategoryController(ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @GetMapping
    public List<ServiceCategory> getAllServices() {
        return serviceCategoryRepository.findAll();
    }
}
