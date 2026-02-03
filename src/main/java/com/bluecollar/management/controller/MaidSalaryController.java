package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.MaidSalaryResponseDTO;
import com.bluecollar.management.service.MaidSalaryService;

@RestController
@RequestMapping("/api/maid-salary")
public class MaidSalaryController {

    private final MaidSalaryService maidSalaryService;

    public MaidSalaryController(MaidSalaryService maidSalaryService) {
        this.maidSalaryService = maidSalaryService;
    }

    @PostMapping("/generate")
    public MaidSalaryResponseDTO generateSalary(
            @RequestParam Long workerId,
            @RequestParam int month,
            @RequestParam int year) {

        return maidSalaryService.generateSalary(workerId, month, year);
    }
}
