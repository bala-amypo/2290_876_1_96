package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "capacity_alerts")
public class CapacityAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String message;

    public CapacityAlert() {
    }

    // ---------- Getters & Setters ----------

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
