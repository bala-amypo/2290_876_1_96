package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Map;

public class CapacityAnalysisResultDto {

    private String teamName;
    private boolean risky;
    private Map<LocalDate, Integer> capacityByDate;

    public CapacityAnalysisResultDto(
            String teamName,
            boolean risky,
            Map<LocalDate, Integer> capacityByDate
    ) {
        this.teamName = teamName;
        this.risky = risky;
        this.capacityByDate = capacityByDate;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isRisky() {
        return risky;
    }

    public Map<LocalDate, Integer> getCapacityByDate() {
        return capacityByDate;
    }
}
