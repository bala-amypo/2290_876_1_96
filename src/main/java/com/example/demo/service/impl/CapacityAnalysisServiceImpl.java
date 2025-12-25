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

        int total = config.getTotalHeadcount();
        int teamSize = employeeRepo.findByTeamNameAndActiveTrue(teamName).size();

        Map<LocalDate, Integer> capacityMap = new HashMap<>();
        boolean risky = false;

        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {

           Long leaves = leaveRepo.countApprovedLeavesOnDate(teamName, d);
           int available = teamSize - leaves.intValue();


            if (total == 0) {
                capacityMap.put(d, 0);
                risky = true;
                continue;
            }

            int percent = (available * 100) / total;
            capacityMap.put(d, percent);

            if (percent < config.getMinCapacityPercent()) {
                risky = true;
            }
        }

        CapacityAnalysisResultDto result =
                new CapacityAnalysisResultDto(risky, capacityMap);
        result.setTeamName(teamName);

        return result;
    }
}
