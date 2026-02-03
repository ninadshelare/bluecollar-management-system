package com.bluecollar.management.service;

import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluecollar.management.dto.MaidSalaryResponseDTO;
import com.bluecollar.management.entity.Attendance;
import com.bluecollar.management.entity.MaidSalary;
import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.WorkerPricing;
import com.bluecollar.management.entity.enums.PricingType;
import com.bluecollar.management.entity.enums.WorkerType;
import com.bluecollar.management.repository.AttendanceRepository;
import com.bluecollar.management.repository.MaidSalaryRepository;
import com.bluecollar.management.repository.WorkerRepository;

@Service
public class MaidSalaryService {

    private final WorkerRepository workerRepository;
    private final AttendanceRepository attendanceRepository;
    private final MaidSalaryRepository maidSalaryRepository;

    public MaidSalaryService(
            WorkerRepository workerRepository,
            AttendanceRepository attendanceRepository,
            MaidSalaryRepository maidSalaryRepository) {
        this.workerRepository = workerRepository;
        this.attendanceRepository = attendanceRepository;
        this.maidSalaryRepository = maidSalaryRepository;
    }

    // ================= GENERATE MONTHLY SALARY =================
    @Transactional
    public MaidSalaryResponseDTO generateSalary(
            Long workerId,
            int month,
            int year) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (worker.getWorkerType() != WorkerType.MAID) {
            throw new RuntimeException("Only MAID salary can be generated");
        }

        YearMonth ym = YearMonth.of(year, month);

        if (maidSalaryRepository.findByWorkerAndSalaryMonth(worker, ym).isPresent()) {
            throw new RuntimeException("Salary already generated for this month");
        }

        WorkerPricing pricing = worker.getPricingList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pricing not found"));

        if (pricing.getPricingType() != PricingType.MONTHLY) {
            throw new RuntimeException("MAID must have MONTHLY pricing");
        }

        List<Attendance> records =
                attendanceRepository.findByWorkerAndWorkDateBetween(
                        worker,
                        ym.atDay(1),
                        ym.atEndOfMonth()
                );

        int presentDays = records.size();
        int totalDays = ym.lengthOfMonth();

        double perDaySalary = pricing.getPrice() / totalDays;
        double finalAmount = perDaySalary * presentDays;

        MaidSalary salary = new MaidSalary();
        salary.setWorker(worker);
        salary.setSalaryMonth(ym);
        salary.setPresentDays(presentDays);
        salary.setMonthlyPrice(pricing.getPrice());
        salary.setCalculatedAmount(finalAmount);
        salary.setPaid(false);

        maidSalaryRepository.save(salary);

        return mapToDTO(salary);
    }

    private MaidSalaryResponseDTO mapToDTO(MaidSalary salary) {

        MaidSalaryResponseDTO dto = new MaidSalaryResponseDTO();
        dto.setSalaryId(salary.getId());
        dto.setWorkerId(salary.getWorker().getId());
        dto.setMonth(salary.getSalaryMonth().toString());
        dto.setPresentDays(salary.getPresentDays());
        dto.setMonthlyPrice(salary.getMonthlyPrice());
        dto.setAmount(salary.getCalculatedAmount());
        dto.setPaid(salary.getPaid());

        return dto;
    }
}
