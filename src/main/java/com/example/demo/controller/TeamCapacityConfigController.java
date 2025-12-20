package com.example.demo.controller;

import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.service.TeamCapacityRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capacity-rules")
public class TeamCapacityConfigController {

    private final TeamCapacityRuleService service;

    public TeamCapacityRuleController(TeamCapacityRuleService service) {
        this.service = service;
    }

    @PostMapping
    public TeamCapacityConfig create(@RequestBody TeamCapacityConfig config) {
        return service.save(config);
    }

    @GetMapping
    public List<TeamCapacityConfig> getAll() {
        return service.getAll();
    }
}
