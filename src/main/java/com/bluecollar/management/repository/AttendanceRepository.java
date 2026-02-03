package com.bluecollar.management.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecollar.management.entity.Attendance;
import com.bluecollar.management.entity.Worker;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // ✅ For CHECK-IN validation
    boolean existsByWorkerAndWorkDate(
            Worker worker,
            LocalDate workDate
    );

    // ✅ For CHECK-OUT
    Optional<Attendance> findByWorkerAndWorkDate(
            Worker worker,
            LocalDate workDate
    );

    // ✅ For MONTHLY attendance summary
    List<Attendance> findByWorkerAndWorkDateBetween(
            Worker worker,
            LocalDate startDate,
            LocalDate endDate
    );
}
