package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.*;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository configRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final CapacityAlertRepository alertRepo;

    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository configRepo,
            EmployeeProfileRepository employeeRepo,
            CapacityAlertRepository alertRepo
    ) {
        this.configRepo = configRepo;
        this.employeeRepo = employeeRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {

        // ✅ INVALID RANGE → IllegalArgumentException
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        // ✅ CONFIG NOT FOUND → IllegalStateException
        TeamCapacityConfig config = configRepo.findByTeamName(teamName)
                .orElseThrow(() ->
                        new IllegalStateException("Config not found"));

        int headcount = employeeRepo
                .findByTeamNameAndActiveTrue(teamName)
                .size();

        Map<LocalDate, Double> capacityByDate = new HashMap<>();
        boolean risky = false;

        // ✅ ZERO HEADCOUNT SAFE
        if (config.getTotalHeadcount() == 0) {
            risky = true;
        }

        for (LocalDate date = startDate;
             !date.isAfter(endDate);
             date = date.plusDays(1)) {

            double capacity =
                    config.getTotalHeadcount() == 0
                            ? 0.0
                            : (headcount * 100.0) / config.getTotalHeadcount();

            capacityByDate.put(date, capacity);

            if (capacity < config.getMinCapacityPercent()) {
                risky = true;

                alertRepo.save(new CapacityAlert(
                        teamName,
                        date,
                        "LOW",
                        "Capacity below threshold"
                ));
            }
        }

        return new CapacityAnalysisResultDto(
                teamName,
                risky,
                capacityByDate
        );
    }
}
