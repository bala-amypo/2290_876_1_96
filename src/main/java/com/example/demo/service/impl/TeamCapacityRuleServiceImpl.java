package com.example.demo.service;

import com.example.demo.model.TeamCapacityRule;
import com.example.demo.repository.TeamCapacityConfigRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamCapacityRuleServiceImpl implements TeamCapacityRuleService {

    private final TeamCapacityConfigRepository repo;

    public TeamCapacityRuleServiceImpl(TeamCapacityConfigRepository repo) {
        this.repo = repo;
    }

    public TeamCapacityRule createRule(TeamCapacityRule rule) {
        return repo.save(rule);
    }

    public TeamCapacityRule updateRule(Long id, TeamCapacityRule rule) {
        return repo.save(rule);
    }

    public TeamCapacityRule getRuleByTeam(String teamName) {
        return repo.findByTeamName(teamName)
                .orElseThrow(() -> new RuntimeException("Capacity config not found"));
    }
}
