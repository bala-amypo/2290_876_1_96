package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "team_capacity_config")
public class TeamCapacityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String teamName;

    @Column(nullable = false)
    private int totalHeadcount;

    @Column(nullable = false)
    private int minCapacityPercent;

    // ✅ NO-ARG CONSTRUCTOR (NAME MUST MATCH CLASS)
    public TeamCapacityConfig() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTotalHeadcount() {
        return totalHeadcount;
    }

    public void setTotalHeadcount(int totalHeadcount) {
        this.totalHeadcount = totalHeadcount;
    }

    public int getMinCapacityPercent() {
        return minCapacityPercent;
    }

    public void setMinCapacityPercent(int minCapacityPercent) {
        this.minCapacityPercent = minCapacityPercent;
    }
}
