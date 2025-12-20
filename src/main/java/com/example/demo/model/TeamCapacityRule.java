package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "team_capacity_config",
    uniqueConstraints = @UniqueConstraint(columnNames = "teamName")
)
public class TeamCapacityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;

    @Column(nullable = false)
    private Integer totalHeadcount;

    @Column(nullable = false)
    private Integer minCapacityPercent;

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTotalHeadcount() {
        return totalHeadcount;
    }

    public void setTotalHeadcount(Integer totalHeadcount) {
        if (totalHeadcount == null || totalHeadcount < 1) {
            throw new IllegalArgumentException("Invalid total headcount");
        }
        this.totalHeadcount = totalHeadcount;
    }

    public Integer getMinCapacityPercent() {
        return minCapacityPercent;
    }

    public void setMinCapacityPercent(Integer minCapacityPercent) {
        if (minCapacityPercent == null || minCapacityPercent < 1 || minCapacityPercent > 100) {
            throw new IllegalArgumentException("Invalid capacity percent");
        }
        this.minCapacityPercent = minCapacityPercent;
    }
}
