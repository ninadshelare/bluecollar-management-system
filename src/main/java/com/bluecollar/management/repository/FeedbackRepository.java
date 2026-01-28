package com.bluecollar.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecollar.management.entity.Feedback;
import com.bluecollar.management.entity.Worker;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByWorker(Worker worker);
}
