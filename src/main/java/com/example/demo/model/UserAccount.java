package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "employee_profile_id")
    private EmployeeProfile employeeProfile;

    // ✅ REQUIRED BY TESTS
    public Long getId() {
        return id;
    }

    // ✅ REQUIRED BY TESTS
    public void setId(Long id) {
        this.id = id;
    }

    // ✅ REQUIRED BY TESTS
    public String getEmail() {
        return email;
    }

    // ✅ REQUIRED BY TESTS
    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public EmployeeProfile getEmployeeProfile() {
        return employeeProfile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmployeeProfile(EmployeeProfile employeeProfile) {
        this.employeeProfile = employeeProfile;
    }
}
