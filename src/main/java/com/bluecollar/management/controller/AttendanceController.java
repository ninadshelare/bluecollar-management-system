package com.bluecollar.management.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.entity.Attendance;
import com.bluecollar.management.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ================= CHECK-IN =================
    @PostMapping("/check-in")
    public Attendance checkIn(
            @RequestParam Long workerId,
            @RequestParam LocalDate workDate) {

        return attendanceService.checkIn(workerId, workDate);
    }

    // ================= CHECK-OUT =================
    @PutMapping("/check-out")
    public Attendance checkOut(
            @RequestParam Long workerId,
            @RequestParam LocalDate workDate) {

        return attendanceService.checkOut(workerId, workDate);
    }

    // ================= MAID MONTHLY SUMMARY =================
    @GetMapping("/maid/{workerId}/summary")
    public int getMonthlyAttendance(
            @PathVariable Long workerId,
            @RequestParam int month,
            @RequestParam int year) {

        return attendanceService.getPresentDaysForMonth(workerId, month, year);
    }
}
