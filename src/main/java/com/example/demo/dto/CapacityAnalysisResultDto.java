package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class CapacityAnalysisResultDto {

    private String teamName;
    private List<LocalDate> lowCapacityDates;

    public CapacityAnalysisResultDto(String teamName, List<LocalDate> lowCapacityDates) {
        this.teamName = teamName;
        this.lowCapacityDates = lowCapacityDates;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<LocalDate> getLowCapacityDates() {
        return lowCapacityDates;
    }
}
