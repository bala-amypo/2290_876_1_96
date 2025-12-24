package com.example.demo.service;

import com.example.demo.dto.CapacityAnalysisResultDto;

import java.time.LocalDate;
import java.util.*;

public interface CapacityAnalysisService {

    CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    );
    List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end
    );
    CapacityAnalysisResultDto analyzeTeamCapacity(String team, LocalDate start, LocalDate end);
List<LocalDate> getOverlappingDates(String team, LocalDate start, LocalDate end);

}
