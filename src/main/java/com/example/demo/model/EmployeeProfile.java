package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private String fullName;
    private String email;
    private String teamName;
    private String role;
    private boolean active;

    @Transient
    private List<EmployeeProfile> colleagues = new ArrayList<>();

    // ✅ Default constructor
    public EmployeeProfile() {}

    // ✅ Parameterized constructor (TEST EXPECTED)
    public EmployeeProfile(
            Long id,
            String employeeId,
            String fullName,
            String email,
            String teamName,
            String role,
            boolean active
    ) {
        this.id = id;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.teamName = teamName;
        this.role = role;
        this.active = active;
    }

    // ✅ Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    // ✅ REQUIRED BY TESTS
    public List<EmployeeProfile> getColleagues() {
        return colleagues;
    }

    public void setColleagues(List<EmployeeProfile> colleagues) {
        this.colleagues = colleagues;
    }
    

}
