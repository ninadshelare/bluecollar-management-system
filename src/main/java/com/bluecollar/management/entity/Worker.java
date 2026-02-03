package com.bluecollar.management.entity;

import java.util.ArrayList;
import java.util.List;

import com.bluecollar.management.entity.enums.WorkerType;

import jakarta.persistence.*;

@Entity
@Table(name = "worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------------- RELATIONS ----------------

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceCategory serviceCategory;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkerPricing> pricingList = new ArrayList<>();

    // ---------------- FIELDS ----------------

    @Enumerated(EnumType.STRING)
    @Column(name = "worker_type", nullable = false)
    private WorkerType workerType;

    private Double rating = 0.0;

    private Boolean available = true;

    private Integer experienceYears;
    
    @Column(nullable = false)
    private Boolean active = true;


    // ---------------- GETTERS & SETTERS ----------------

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public List<WorkerPricing> getPricingList() {
        return pricingList;
    }

    public WorkerType getWorkerType() {
        return workerType;
    }

    public Double getRating() {
        return rating;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public void setPricingList(List<WorkerPricing> pricingList) {
        this.pricingList = pricingList;
    }

    public void setWorkerType(WorkerType workerType) {
        this.workerType = workerType;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
}
