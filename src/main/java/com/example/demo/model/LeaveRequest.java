package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private boolean approved = false;

    public Long getId() { return id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
