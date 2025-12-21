package com.example.demo.service;

import java.time.LocalDate;

public interface CapacityAnalysisService {

    List<LocalDate> analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    );
}

package com.example.demo.service;

import com.example.demo.dto.CapacityAnalysisResultDto;

import java.time.LocalDate;



public interface CapacityAnalysisService {

    CapacityAnalysisResultDto analyzeTeamCapacity(
            String userEmail,
            LocalDate startDate,
            LocalDate endDate
    );
}
