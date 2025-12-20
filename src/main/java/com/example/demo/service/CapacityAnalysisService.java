package com.example.demo.service;

import com.example.demo.model.CapacityAlert;

import java.time.LocalDate;
import java.util.List;

public interface CapacityAnalysisService {
    List<CapacityAlert> analyzeCapacity(
            String team,
            LocalDate startDate,
            LocalDate endDate
    );
}
