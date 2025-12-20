package com.example.demo.model;

import java.time.LocalDate;

public class LeaveRequest {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public LeaveRequest() {}

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
