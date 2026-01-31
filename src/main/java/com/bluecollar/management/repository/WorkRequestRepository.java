package com.bluecollar.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecollar.management.entity.WorkRequest;
import com.bluecollar.management.entity.Worker;

public interface WorkRequestRepository extends JpaRepository<WorkRequest, Long> {

    List<WorkRequest> findByWorker(Worker worker);
}
