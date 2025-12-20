package com.example.demo.controller;

import com.example.demo.model.TeamCapacityRule;
import com.example.demo.service.TeamCapacityRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/capacity-config")
public class TeamCapacityRuleController {

    private final TeamCapacityRuleService ruleService;

    public TeamCapacityRuleController(
            TeamCapacityRuleService ruleService
    ) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public ResponseEntity<TeamCapacityRule> save(
            @RequestBody TeamCapacityRule config
    ) {
        return ResponseEntity.ok(ruleService.saveConfig(config));
    }
}
