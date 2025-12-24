
package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Map;
public class CapacityAnalysisResultDto {

    private final String teamName;
    private final boolean risky;
    private final Map<LocalDate, Double> capacityByDate;

    public CapacityAnalysisResultDto(
            String teamName,
            boolean risky,
            Map<LocalDate, Double> capacityByDate
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

    public Map<LocalDate, Double> getCapacityByDate() {
        return capacityByDate;
    }
}
