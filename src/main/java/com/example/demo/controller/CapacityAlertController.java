package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/capacity-alerts")
public class CapacityAlertController {

    @PostMapping("/analyze")
    public String analyze() {
        return "Analysis triggered";
    }

    @GetMapping("/team/{teamName}")
    public String getAlerts(@PathVariable String teamName) {
        return "Alerts fetched";
    }
}
