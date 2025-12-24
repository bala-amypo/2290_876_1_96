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
    private final LeaveRequestRepository leaveRepo;
    private final CapacityAlertRepository alertRepo;

    // ✅ REQUIRED BY TESTS
    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository configRepo,
            EmployeeProfileRepository employeeRepo,
            LeaveRequestRepository leaveRepo,
            CapacityAlertRepository alertRepo
    ) {
        this.configRepo = configRepo;
        this.employeeRepo = employeeRepo;
        this.leaveRepo = leaveRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new BadRequestException("Invalid date range");
        }

        TeamCapacityConfig config = configRepo.findByTeamName(teamName)
                .orElseThrow(() -> new BadRequestException("Config not found"));

        Map<LocalDate, Integer> capacityByDate = new HashMap<>();

        int total = config.getTotalHeadcount();
        int active = employeeRepo.findByTeamNameAndActiveTrue(teamName).size();

        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
            capacityByDate.put(d, (active * 100) / total);
        }

        boolean risky = capacityByDate.values()
                .stream()
                .anyMatch(p -> p < config.getMinCapacityPercent());

        return new CapacityAnalysisResultDto(teamName, capacityByDate, risky);
    }
}
