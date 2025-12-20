package com.example.demo.service;

import com.example.demo.model.TeamCapacityConfig;
import java.util.List;

public interface TeamCapacityConfigService {
    TeamCapacityConfig save(TeamCapacityConfig config);
    List<TeamCapacityConfig> getAll();
}
