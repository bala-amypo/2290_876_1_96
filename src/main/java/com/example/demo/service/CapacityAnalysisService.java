package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

public interface CapacityAnalysisService {

    // ✅ REQUIRED BY TESTS
    List<LocalDate> analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    );
}
