package com.bluecollar.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecollar.management.entity.WorkRequest;

public interface WorkRequestRepository extends JpaRepository<WorkRequest, Long> {
}
