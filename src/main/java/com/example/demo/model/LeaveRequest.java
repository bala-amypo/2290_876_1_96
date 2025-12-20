package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EmployeeProfile employee;

    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String status;
    private String reason;

    public LeaveRequest() {}

    public LeaveRequest(Long id, EmployeeProfile employee, LocalDate startDate,
                        LocalDate endDate, String type, String status, String reason) {
        this.id = id;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = status;
        this.reason = reason;
    }

    public Long getId() { return id; }
    public EmployeeProfile getEmployee() { return employee; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
