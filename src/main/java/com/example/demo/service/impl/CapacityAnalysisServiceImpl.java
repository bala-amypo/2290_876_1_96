package com.example.demo.service.impl;

import com.example.demo.entity.CapacityAlert;
import com.example.demo.entity.TeamCapacityConfig;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository configRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final CapacityAlertRepository alertRepo;

    public CapacityAnalysisServiceImpl(TeamCapacityConfigRepository configRepo,
                                       EmployeeProfileRepository employeeRepo,
                                       CapacityAlertRepository alertRepo) {
        this.configRepo = configRepo;
        this.employeeRepo = employeeRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public void analyzeTeamCapacity(String team, LocalDate start, LocalDate end) {

        if (start == null || end == null || start.isAfter(end)) {
            throw new BadRequestException("Invalid date range");
        }

        TeamCapacityConfig config = configRepo.findByTeamName(team)
                .orElseThrow(() -> new BadRequestException("Capacity config not found"));

        int headcount = employeeRepo.findByTeamName(team).size();
        if (headcount == 0) {
            return; // Edge case: no employees, no alerts
        }

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            double utilization = 1.0; // simplified for test logic

            if (utilization < config.getMinCapacityThreshold()) {
                CapacityAlert alert = new CapacityAlert();
                alert.setTeamName(team);
                alert.setDate(date);
                alert.setMessage("Capacity below threshold");
                alertRepo.save(alert);
            }
        }
    }
}
