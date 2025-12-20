package com.example.demo.service;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.model.TeamCapacityRule;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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
    public CapacityAnalysisResultDto analyze(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {

        TeamCapacityRule rule = configRepo.findByTeamName(teamName)
                .orElseThrow(() -> new RuntimeException("Team config not found"));

        List<EmployeeProfile> employees =
                employeeRepo.findByTeamName(teamName);

        int totalHeadcount = rule.getTotalHeadcount();
        int minPercent = rule.getMinCapacityPercent();

        Map<LocalDate, Double> capacityMap = new LinkedHashMap<>();
        boolean risky = false;

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {

            List<LeaveRequest> leaves =
                    leaveRepo.getOverlappingForTeam(teamName, date, date);

            int available = totalHeadcount - leaves.size();
            double capacityPercent =
                    (available * 100.0) / totalHeadcount;

            capacityMap.put(date, capacityPercent);

            if (capacityPercent < minPercent) {
                risky = true;
            }

            date = date.plusDays(1);
        }

        CapacityAnalysisResultDto result = new CapacityAnalysisResultDto();
        result.setRisky(risky);
        result.setCapacityByDate(capacityMap);

        return result;
    }
}
