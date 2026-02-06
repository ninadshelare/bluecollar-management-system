package com.bluecollar.management.service;

import org.springframework.stereotype.Service;

import com.bluecollar.management.dto.WorkerEarningsResponseDTO;
import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.enums.PaymentStatus;
import com.bluecollar.management.repository.PaymentRepository;
import com.bluecollar.management.repository.WorkerRepository;

@Service
public class WorkerEarningsService {

    private final PaymentRepository paymentRepository;
    private final WorkerRepository workerRepository;

    public WorkerEarningsService(
            PaymentRepository paymentRepository,
            WorkerRepository workerRepository) {
        this.paymentRepository = paymentRepository;
        this.workerRepository = workerRepository;
    }

    public WorkerEarningsResponseDTO getWorkerEarnings(Long workerId) {

       workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Double total = paymentRepository.getTotalEarnings(workerId);
        Double paid = paymentRepository.getEarningsByStatus(workerId, PaymentStatus.PAID);
        Double pending = paymentRepository.getEarningsByStatus(workerId, PaymentStatus.PENDING);

        WorkerEarningsResponseDTO dto = new WorkerEarningsResponseDTO();
        dto.setWorkerId(workerId);
        dto.setTotalEarnings(total);
        dto.setPaidEarnings(paid);
        dto.setPendingEarnings(pending);
        dto.setTotalJobs(
                paymentRepository.findByWorker_Id(workerId).size()
        );

        return dto;
    }
}
