package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.CapacityAlert;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
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

        int totalEmployees = employeeRepo.findByTeamNameAndActiveTrue(teamName).size();

        Map<LocalDate, Integer> capacityByDate = new HashMap<>();
        boolean risky = false;

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

            int approvedLeaves =
                    leaveRepo.countApprovedLeavesOnDate(teamName, date);

            int available = totalEmployees - approvedLeaves;
            capacityByDate.put(date, available);

            // 🚨 Zero headcount handled
            if (totalEmployees == 0) {
                risky = true;
                continue;
            }

            double percent = (available * 100.0) / totalEmployees;

            if (percent < config.getMinCapacityPercent()) {
                risky = true;

                alertRepo.save(new CapacityAlert(
                        teamName,
                        date,
                        "LOW",
                        "Capacity below threshold"
                ));
            }
        }

        CapacityAnalysisResultDto result = new CapacityAnalysisResultDto();
        result.setTeamName(teamName);
        result.setCapacityByDate(capacityByDate);
        result.setRisky(risky);

        return result;
    }
}
