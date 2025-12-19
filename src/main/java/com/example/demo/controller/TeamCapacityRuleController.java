package com.example.demo.controller;

import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.service.TeamCapacityRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/capacity-rules")
public class TeamCapacityRuleController {

    private final TeamCapacityRuleService service;

    public TeamCapacityRuleController(TeamCapacityRuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TeamCapacityConfig> create(
            @RequestBody TeamCapacityConfig rule) {
        return ResponseEntity.ok(service.createRule(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamCapacityConfig> update(
            @PathVariable Long id,
            @RequestBody TeamCapacityConfig rule) {
        return ResponseEntity.ok(service.updateRule(id, rule));
    }

    @GetMapping("/{team}")
    public ResponseEntity<TeamCapacityConfig> getByTeam(
            @PathVariable String team) {
        return ResponseEntity.ok(service.getRuleByTeam(team));
    }
}
