package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.TeamCapacityRuleService;
import org.springframework.stereotype.Service;

@Service
public class TeamCapacityRuleServiceImpl implements TeamCapacityRuleService {

    private final TeamCapacityConfigRepository capacityRepo;

    public TeamCapacityRuleServiceImpl(TeamCapacityConfigRepository capacityRepo) {
        this.capacityRepo = capacityRepo;
    }

    @Override
    public TeamCapacityConfig createRule(TeamCapacityConfig rule) {
        validate(rule);
        return capacityRepo.save(rule);
    }

    @Override
    public TeamCapacityConfig updateRule(Long id, TeamCapacityConfig rule) {
        TeamCapacityConfig existing = capacityRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity config not found"));

        validate(rule);
        existing.setTeamName(rule.getTeamName());
        existing.setTotalHeadcount(rule.getTotalHeadcount());
        existing.setMinCapacityPercent(rule.getMinCapacityPercent());

        return capacityRepo.save(existing);
    }

    @Override
    public TeamCapacityConfig getRuleByTeam(String teamName) {
        return capacityRepo.findByTeamName(teamName)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity config not found"));
    }

    private void validate(TeamCapacityConfig rule) {
        if (rule.getTotalHeadcount() == null || rule.getTotalHeadcount() < 1) {
            throw new BadRequestException("Invalid total headcount");
        }
        if (rule.getMinCapacityPercent() < 1 || rule.getMinCapacityPercent() > 100) {
            throw new BadRequestException("Invalid capacity percent");
        }
    }
}
