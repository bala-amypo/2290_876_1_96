package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "team_capacity_configs",
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

    public TeamCapacityConfig() {
    }

    // ---------- Getters & Setters ----------

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
