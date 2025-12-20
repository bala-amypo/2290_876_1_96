package com.example.demo.service.impl;

import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.model.TeamCapacityRule;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.TeamCapacityRuleService;
import org.springframework.stereotype.Service;

@Service
public class TeamCapacityRuleServiceImpl implements TeamCapacityRuleService {

    private final TeamCapacityConfigRepository repository;

    // ✅ Constructor Injection (MANDATORY)
    public TeamCapacityRuleServiceImpl(TeamCapacityConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamCapacityRule createRule(TeamCapacityRule rule) {
        TeamCapacityConfig saved = repository.save(rule);
        return (TeamCapacityRule) saved;
    }

    @Override
    public TeamCapacityRule updateRule(Long id, TeamCapacityRule updatedRule) {
        TeamCapacityConfig existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capacity config not found"));

        existing.setTeamName(updatedRule.getTeamName());
        existing.setTotalHeadcount(updatedRule.getTotalHeadcount());
        existing.setMinCapacityPercent(updatedRule.getMinCapacityPercent());

        return (TeamCapacityRule) repository.save(existing);
    }

    @Override
    public TeamCapacityRule getRuleByTeam(String teamName) {
        return (TeamCapacityRule) repository.findByTeamName(teamName)
                .orElseThrow(() -> new RuntimeException("Capacity config not found"));
    }
}
