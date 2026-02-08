package com.bluecollar.management.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluecollar.management.entity.Attendance;
import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.enums.WorkerType;
import com.bluecollar.management.repository.AttendanceRepository;
import com.bluecollar.management.repository.WorkerRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final WorkerRepository workerRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            WorkerRepository workerRepository) {
        this.attendanceRepository = attendanceRepository;
        this.workerRepository = workerRepository;
    }

    // ================= CHECK-IN =================
    @Transactional
    public Attendance checkIn(Long workerId, LocalDate workDate) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (worker.getWorkerType() != WorkerType.MAID
                && worker.getWorkerType() != WorkerType.LABOUR) {
            throw new RuntimeException("Attendance allowed only for MAID or LABOUR");
        }

        if (attendanceRepository.existsByWorkerAndWorkDate(worker, workDate)) {
            throw new RuntimeException("Already checked in for today");
        }

        Attendance attendance = new Attendance();
        attendance.setWorker(worker);
        attendance.setWorkDate(workDate);
        attendance.setCheckIn(java.time.LocalDateTime.now());

        return attendanceRepository.save(attendance);
    }

    // ================= CHECK-OUT =================
    @Transactional
    public Attendance checkOut(Long workerId, LocalDate workDate) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Attendance attendance = attendanceRepository
                .findByWorkerAndWorkDate(worker, workDate)
                .orElseThrow(() -> new RuntimeException("Check-in not found"));

        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Already checked out");
        }

        attendance.setCheckOut(java.time.LocalDateTime.now());

        long hours = Duration
                .between(attendance.getCheckIn(), attendance.getCheckOut())
                .toHours();

        attendance.setTotalHours((double) hours);

        return attendanceRepository.save(attendance);
    }

    // ================= MAID MONTHLY SUMMARY =================
    public int getPresentDaysForMonth(Long workerId, int month, int year) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (worker.getWorkerType() != WorkerType.MAID) {
            throw new RuntimeException("Only MAID has monthly attendance");
        }

        YearMonth ym = YearMonth.of(year, month);

        List<Attendance> records =
                attendanceRepository.findByWorkerAndWorkDateBetween(
                        worker,
                        ym.atDay(1),
                        ym.atEndOfMonth()
                );

        return records.size();
    }
}
