package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Map;

public class CapacityAnalysisResultDto {

    private String teamName;
    private boolean risky;
    private Map<LocalDate, Integer> capacityByDate;

    public CapacityAnalysisResultDto(boolean risky, Map<LocalDate, Integer> capacityByDate) {
        this.risky = risky;
        this.capacityByDate = capacityByDate;
    }

    public CapacityAnalysisResultDto() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isRisky() {
        return risky;
    }

    public void setRisky(boolean risky) {
        this.risky = risky;
    }

    public Map<LocalDate, Integer> getCapacityByDate() {
        return capacityByDate;
    }

    public void setCapacityByDate(Map<LocalDate, Integer> capacityByDate) {
        this.capacityByDate = capacityByDate;
    }
}
