package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class CapacityAlert {

    @Id
    @GeneratedValue
    private Long id;

    private String teamName;
    private LocalDate date;
    private String severity;
    private String message;
}
