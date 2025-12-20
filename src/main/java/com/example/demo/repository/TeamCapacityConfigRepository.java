package com.example.demo.repository;

import com.example.demo.model.TeamCapacityRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamCapacityConfigRepository
        extends JpaRepository<TeamCapacityRule, Long> {

    Optional<TeamCapacityRule> findByTeamName(String teamName);
}
