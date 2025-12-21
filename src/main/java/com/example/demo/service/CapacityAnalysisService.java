package com.example.demo.service;

import com.example.demo.dto.CapacityAnalysisResultDto;

import java.time.LocalDate;

import java.util.List;


public interface CapacityAnalysisService {

    CapacityAnalysisResultDto analyzeTeamCapacity(
            String userEmail,
            LocalDate startDate,
            LocalDate endDate
    );
}
