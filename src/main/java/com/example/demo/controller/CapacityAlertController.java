package com.example.demo.controller;

import com.example.demo.model.CapacityAlert;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capacity")
public class CapacityAlertController {

    @Autowired
    private CapacityAnalysisService capacityAnalysisService;

    @GetMapping("/alerts")
    public List<CapacityAlert> getAlerts() {
        return capacityAnalysisService.generateAlerts();
    }
}
