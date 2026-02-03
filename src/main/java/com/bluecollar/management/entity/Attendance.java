package com.bluecollar.management.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attendance",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"worker_id", "work_date"}
       ))
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "work_request_id")
    private WorkRequest workRequest; // null for MAID

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    private Double totalHours;

    // getters & setters
}
