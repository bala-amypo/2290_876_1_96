package com.example.demo.service;

import com.example.demo.dto.CapacityAnalysisResultDto;

import java.time.LocalDate;
import java.util.List;

public interface CapacityAnalysisService {

    List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end
    );

    CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate start,
            LocalDate end
    );
}
