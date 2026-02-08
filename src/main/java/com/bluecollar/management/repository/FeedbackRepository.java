package com.bluecollar.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecollar.management.entity.Feedback;
import com.bluecollar.management.entity.WorkRequest;
import com.bluecollar.management.entity.Worker;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByWorker(Worker worker);

    Optional<Feedback> findByWorkRequest(WorkRequest workRequest);

    boolean existsByWorkRequest(WorkRequest workRequest);
}
