package com.bluecollar.management.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bluecollar.management.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByWorkRequestId(Long workRequestId);
}
