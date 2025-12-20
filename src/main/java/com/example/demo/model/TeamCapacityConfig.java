package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class TeamCapacityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team;
    private Integer maxCapacity;

    
    public TeamCapacityConfig() {}

    
    public TeamCapacityConfig(String team, Integer maxCapacity) {
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
