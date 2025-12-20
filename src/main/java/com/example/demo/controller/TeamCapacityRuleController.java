package com.example.demo.controller;

import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.service.TeamCapacityRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/capacity-config")
public class TeamCapacityConfigController {

    private final TeamCapacityRuleService ruleService;

    public TeamCapacityConfigController(
            TeamCapacityRuleService ruleService
    ) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public ResponseEntity<TeamCapacityConfig> save(
            @RequestBody TeamCapacityConfig config
    ) {
        return ResponseEntity.ok(ruleService.saveConfig(config));
    }
}
