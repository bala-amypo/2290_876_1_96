package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class TeamCapacityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String teamName;

    private Integer totalHeadcount;
    private Integer minCapacityPercent;

    public TeamCapacityRule() {}

    public TeamCapacityRule(Long id, String teamName,
                            Integer totalHeadcount, Integer minCapacityPercent) {
        this.id = id;
        this.teamName = teamName;
        this.totalHeadcount = totalHeadcount;
        this.minCapacityPercent = minCapacityPercent;
    }

    public String getTeamName() { return teamName; }
    public Integer getTotalHeadcount() { return totalHeadcount; }
    public Integer getMinCapacityPercent() { return minCapacityPercent; }
}
