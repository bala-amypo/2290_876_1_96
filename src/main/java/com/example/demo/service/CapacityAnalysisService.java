package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

public interface CapacityAnalysisService {

    List<LocalDate> getOverlappingDatesForTeam(
            String teamName,
            LocalDate start,
            LocalDate end
    );
}
