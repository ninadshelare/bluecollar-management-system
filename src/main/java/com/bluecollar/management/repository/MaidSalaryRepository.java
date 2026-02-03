package com.bluecollar.management.repository;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecollar.management.entity.MaidSalary;
import com.bluecollar.management.entity.Worker;

public interface MaidSalaryRepository
        extends JpaRepository<MaidSalary, Long> {

    Optional<MaidSalary> findByWorkerAndSalaryMonth(
            Worker worker,
            YearMonth salaryMonth
    );
}
