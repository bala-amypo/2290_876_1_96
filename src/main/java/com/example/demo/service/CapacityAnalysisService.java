package com.example.demo.service;

import java.time.LocalDate;

public interface CapacityAnalysisService {

    void analyzeTeamCapacity(
            String team,
            LocalDate start,
            LocalDate end
    );
}
