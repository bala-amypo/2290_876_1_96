package com.example.demo.repository;

import com.example.demo.model.TeamCapacityRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamCapacityConfigRepository extends JpaRepository<TeamCapacityRule, Long> {
    TeamCapacityRule findByTeamName(String teamName);
}
