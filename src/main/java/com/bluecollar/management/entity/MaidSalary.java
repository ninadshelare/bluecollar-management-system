package com.bluecollar.management.entity;

import java.time.YearMonth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "maid_salary",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"worker_id", "salary_month"}
       ))
public class MaidSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(name = "salary_month", nullable = false)
    private YearMonth salaryMonth;

    private Integer presentDays;

    private Double monthlyPrice;

    private Double calculatedAmount;

    private Boolean paid = false;
}
