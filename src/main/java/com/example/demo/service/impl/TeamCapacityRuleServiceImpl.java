package com.example.demo.service.impl;

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
        return repository.save(rule);
    }

    @Override
    public TeamCapacityRule updateRule(Long id, TeamCapacityRule updatedRule) {
        TeamCapacityRule existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capacity config not found"));

        existing.setTeamName(updatedRule.getTeamName());
        existing.setTotalHeadcount(updatedRule.getTotalHeadcount());
        existing.setMinCapacityPercent(updatedRule.getMinCapacityPercent());

        return repository.save(existing);
    }

    @Override
    public TeamCapacityRule getRuleByTeam(String teamName) {
        return repository.findByTeamName(teamName)
                .orElseThrow(() -> new RuntimeException("Capacity config not found"));
    }
}
