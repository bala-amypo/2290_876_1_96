package com.example.demo.service;

import com.example.demo.model.TeamCapacityRule;

public interface TeamCapacityRuleService {
    TeamCapacityRule createRule(TeamCapacityRule rule);
    TeamCapacityRule updateRule(Long id, TeamCapacityRule rule);
    TeamCapacityRule getRuleByTeam(String teamName);
}
