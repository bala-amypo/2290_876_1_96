package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String role;

    // 🔗 REQUIRED BY TESTS
    @OneToOne
    @JoinColumn(name = "employee_profile_id")
    private EmployeeProfile employeeProfile;

    // =======================
    // ✅ REQUIRED GETTERS
    // =======================

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public EmployeeProfile getEmployeeProfile() {
        return employeeProfile;
    }

    // =======================
    // ✅ REQUIRED SETTERS
    // =======================

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmployeeProfile(EmployeeProfile employeeProfile) {
        this.employeeProfile = employeeProfile;
    }
}
