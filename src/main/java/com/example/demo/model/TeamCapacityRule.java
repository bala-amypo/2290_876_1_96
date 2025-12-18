package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class TeamCapacityRule {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String teamName;

    private Integer totalHeadcount;
    private Integer minCapacityPercent;
}
