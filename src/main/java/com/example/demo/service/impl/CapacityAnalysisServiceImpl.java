package com.example.demo.service.impl;

import com.example.demo.model.CapacityAlert;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository configRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final LeaveRequestRepository leaveRepo;
    private final CapacityAlertRepository alertRepo;

    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository configRepo,
            EmployeeProfileRepository employeeRepo,
            LeaveRequestRepository leaveRepo,
            CapacityAlertRepository alertRepo) {

        this.configRepo = configRepo;
        this.employeeRepo = employeeRepo;
        this.leaveRepo = leaveRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public void analyzeTeamCapacity(
            String team,
            LocalDate start,
            LocalDate end) {

        TeamCapacityConfig config =
                configRepo.findByTeam(team).orElseThrow();

        int maxAllowed = config.getMaxPercentage();

        int totalEmployees = (int) employeeRepo.findAll()
                .stream()
                .filter(e -> team.equals(e.getTeam()) && e.isActive())
                .count();

        int onLeave = leaveRepo
                .findByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        "APPROVED", end, start)
                .size();

        int percent = totalEmployees == 0
                ? 0
                : (onLeave * 100) / totalEmployees;

        if (percent > maxAllowed) {
            CapacityAlert alert = new CapacityAlert(
                    team,
                    "CAPACITY_EXCEEDED",
                    start,
                    end
            );
            alertRepo.save(alert);
        }
    }
}
