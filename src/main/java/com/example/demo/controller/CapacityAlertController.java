package com.example.demo.controller;

import com.example.demo.model.CapacityAlert;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/capacity-alerts")
public class CapacityAlertController {

    private final CapacityAnalysisService analysisService;

    public CapacityAlertController(
            CapacityAnalysisService analysisService
    ) {
        this.analysisService = analysisService;
    }

    @GetMapping("/{teamName}")
    public ResponseEntity<List<CapacityAlert>> analyze(
            @PathVariable String teamName,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end
    ) {
        return ResponseEntity.ok(
                analysisService.analyzeCapacity(teamName, start, end)
        );
    }
}
