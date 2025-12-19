package com.example.demo.controller;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/capacity-alerts")
public class CapacityAlertController {

    private final CapacityAnalysisService capacityAnalysisService;

    public CapacityAlertController(CapacityAnalysisService capacityAnalysisService) {
        this.capacityAnalysisService = capacityAnalysisService;
    }

   
    @GetMapping("/analyze")
    public ResponseEntity<CapacityAnalysisResultDto> analyzeAndGenerateAlerts(
            @RequestParam String team,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return ResponseEntity.ok(
                capacityAnalysisService.analyzeTeamCapacity(team, start, end)
        );
    }
}
