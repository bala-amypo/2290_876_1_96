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

    @GetMapping("/analyze")
    public List<String> analyze(
        @RequestParam String teamName,
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate) {

    return analysisService.analyzeCapacity(teamName, startDate, endDate);
}

}
