package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "teamName"))
public class TeamCapacityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private Integer totalHeadcount;
    private Integer minCapacityPercent;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public Integer getTotalHeadcount() { return totalHeadcount; }
    public void setTotalHeadcount(Integer totalHeadcount) {
        if (totalHeadcount < 1) {
            throw new IllegalArgumentException("Invalid total headcount");
        }
        this.totalHeadcount = totalHeadcount;
    }

    public Integer getMinCapacityPercent() { return minCapacityPercent; }
    public void setMinCapacityPercent(Integer minCapacityPercent) {
        if (minCapacityPercent < 1 || minCapacityPercent > 100) {
            throw new IllegalArgumentException("Invalid capacity percent");
        }
        this.minCapacityPercent = minCapacityPercent;
    }
}
