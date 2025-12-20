package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

public interface CapacityAnalysisService {

    List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end
    );

    void analyzeTeamCapacity(
            String teamName,
            LocalDate start,
            LocalDate end
    );
}
