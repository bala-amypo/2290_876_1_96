package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private EmployeeProfile employee;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status; // PENDING / APPROVED / REJECTED

    private String reason;

    public LeaveRequest() {}

    public LeaveRequest(EmployeeProfile employee, LocalDate startDate,
                        LocalDate endDate, String type, String status, String reason) {
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
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getReason() { return reason; }

    public void setStatus(String status) { this.status = status; }
}
