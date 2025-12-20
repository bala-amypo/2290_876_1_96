package com.example.demo.service.impl;

import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.TeamCapacityConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamCapacityConfigServiceImpl implements TeamCapacityConfigService {

    private final TeamCapacityConfigRepository repository;

    public TeamCapacityConfigServiceImpl(TeamCapacityConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamCapacityConfig save(TeamCapacityConfig config) {
        return repository.save(config);
    }

    @Override
    public List<TeamCapacityConfig> getAll() {
        return repository.findAll();
    }
}
