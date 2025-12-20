package com.example.demo.controller;

import com.example.demo.model.TeamCapacityRule;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/capacity-rules")
public class TeamCapacityRuleController {

    @PostMapping
    public String create(@RequestBody TeamCapacityRule rule) {
        return "Rule created";
    }

    @GetMapping("/team/{teamName}")
    public String get(@PathVariable String teamName) {
        return "Rule fetched";
    }
}
