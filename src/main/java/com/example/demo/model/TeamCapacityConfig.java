package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class TeamCapacityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team;
    private int maxPercentage;

    public Long getId() { return id; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public int getMaxPercentage() { return maxPercentage; }
    public void setMaxPercentage(int maxPercentage) {
        this.maxPercentage = maxPercentage;
    }
}
