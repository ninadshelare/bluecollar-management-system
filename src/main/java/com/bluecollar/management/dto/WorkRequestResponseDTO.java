package com.bluecollar.management.dto;

import java.time.LocalDateTime;

public class WorkRequestResponseDTO {

    private Long requestId;
    private String status;
    private String serviceName;
    private LocalDateTime requestedAt;

    private CustomerSummaryDTO customer;
    private WorkerSummaryDTO worker;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public CustomerSummaryDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerSummaryDTO customer) {
        this.customer = customer;
    }

    public WorkerSummaryDTO getWorker() {
        return worker;
    }

    public void setWorker(WorkerSummaryDTO worker) {
        this.worker = worker;
    }
}
