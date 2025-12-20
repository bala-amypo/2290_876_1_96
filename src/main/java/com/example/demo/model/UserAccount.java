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

    // ✅ REQUIRED BY TESTS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {   // ← REQUIRED
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {       // ← REQUIRED
        return role;
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
    public String getPassword() {
    return password;
}

public String getEmail() {
    return email;
}

public String getRole() {
    return role;
}

}
