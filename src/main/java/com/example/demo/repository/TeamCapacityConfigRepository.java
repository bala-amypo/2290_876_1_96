package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.TeamCapacityConfig;

import java.util.Optional;

public interface TeamCapacityConfigRepository
        extends JpaRepository<TeamCapacityConfig, Long> {

    Optional<TeamCapacityConfig> findByTeam(String team);
}
