package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class CapacityAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team;
    private LocalDate date;
    private String message;
    private String severity;

    // ✅ REQUIRED
    public CapacityAlert() {}

    // ✅ REQUIRED BY TESTS
    public CapacityAlert(String team, LocalDate date,
                         String message, String severity) {
        this.team = team;
        this.date = date;
        this.message = message;
        this.severity = severity;
    }
}
