package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private EmployeeProfile employee;

    private LocalDate startDate;
    private LocalDate endDate;

    private String type;     // CASUAL / SICK / etc
    private String status;   // PENDING / APPROVED / REJECTED
    private String reason;

    public LeaveRequest() {}

    /* getters & setters */

    public Long getId() {
        return id;
    }

    public EmployeeProfile getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployee(EmployeeProfile employee) {
        this.employee = employee;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
