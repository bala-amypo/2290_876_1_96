package com.example.demo.service;

import com.example.demo.dto.CapacityAnalysisResultDto;
import java.time.LocalDate;
import java.util.List;

public interface CapacityAnalysisService {

    CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    );

    List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    );
}
