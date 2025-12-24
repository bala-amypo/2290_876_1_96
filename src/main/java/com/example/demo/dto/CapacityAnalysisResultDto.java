package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Map;

public class CapacityAnalysisResultDto {

    private String teamName;
    private Map<LocalDate, Integer> capacityByDate;
    private boolean risky;

    public CapacityAnalysisResultDto(
            String teamName,
            Map<LocalDate, Integer> capacityByDate,
            boolean risky
    ) {
        this.teamName = teamName;
        this.capacityByDate = capacityByDate;
        this.risky = risky;
    }

    public String getTeamName() {
        return teamName;
    }

    public Map<LocalDate, Integer> getCapacityByDate() {
        return capacityByDate;
    }

    public boolean isRisky() {
        return risky;
    }
}
