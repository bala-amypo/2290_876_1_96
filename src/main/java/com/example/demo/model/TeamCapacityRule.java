package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class TeamCapacityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team;
    private Integer maxCapacity;

    // ✅ REQUIRED: No-args constructor
    public TeamCapacityRule() {}

    // ✅ REQUIRED: All-args constructor
    public TeamCapacityRule(String team, Integer maxCapacity) {
        this.team = team;
        this.maxCapacity = maxCapacity;
    }

    public Long getId() {
        return id;
    }

    public String getTeam() {
        return team;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }
}
