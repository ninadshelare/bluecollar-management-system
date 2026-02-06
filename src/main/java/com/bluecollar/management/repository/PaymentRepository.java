package com.bluecollar.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bluecollar.management.entity.Payment;
import com.bluecollar.management.entity.enums.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

 
    Optional<Payment> findByWorkRequestId(Long workRequestId);

    // All payments of a worker
    List<Payment> findByWorker_Id(Long workerId);

    // Paid / Pending split
    List<Payment> findByWorker_IdAndStatus(Long workerId, PaymentStatus status);

    // Total earnings (PAID + PENDING)
    @Query("""
        SELECT COALESCE(SUM(p.amount), 0)
        FROM Payment p
        WHERE p.worker.id = :workerId
    """)
    Double getTotalEarnings(Long workerId);

    // Only PAID earnings
    @Query("""
        SELECT COALESCE(SUM(p.amount), 0)
        FROM Payment p
        WHERE p.worker.id = :workerId
          AND p.status = :status
    """)
    Double getEarningsByStatus(Long workerId, PaymentStatus status);
}
