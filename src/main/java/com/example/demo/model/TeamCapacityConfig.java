package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "team_capacity_config",
    uniqueConstraints = @UniqueConstraint(columnNames = "teamName")
)
public class TeamCapacityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;

    @Column(nullable = false)
    private Integer totalHeadcount;

    @Column(nullable = false)
    private Integer minCapacityPercent;

    // ===== VALIDATION HELPERS =====
    @PrePersist
    @PreUpdate
    public void validate() {
        if (totalHeadcount == null || totalHeadcount < 1) {
            throw new IllegalArgumentException("Invalid total headcount");
        }
        if (minCapacityPercent == null || minCapacityPercent < 1 || minCapacityPercent > 100) {
            throw new IllegalArgumentException("Invalid capacity percent");
        }
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public Integer getTotalHeadcount() {
        return totalHeadcount;
    }

    public Integer getMinCapacityPercent() {
        return minCapacityPercent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTotalHeadcount(Integer totalHeadcount) {
        this.totalHeadcount = totalHeadcount;
    }

    public void setMinCapacityPercent(Integer minCapacityPercent) {
        this.minCapacityPercent = minCapacityPercent;
    }
}
xxxxxxxxx