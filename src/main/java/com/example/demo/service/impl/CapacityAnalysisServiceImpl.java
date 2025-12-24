package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository configRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final LeaveRequestRepository leaveRepo;
    private final CapacityAlertRepository alertRepo;

    // ✅ MUST MATCH TEST CONSTRUCTOR
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

        // ✅ INVALID DATE RANGE
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new BadRequestException("Invalid date range");
        }

        // ✅ CONFIG NOT FOUND
        TeamCapacityConfig config = configRepo.findByTeamName(teamName)
                .orElseThrow(() -> new BadRequestException("Config not found"));

        int totalHeadcount = config.getTotalHeadcount();

        // ✅ ZERO HEADCOUNT EDGE CASE
        if (totalHeadcount == 0) {
            return new CapacityAnalysisResultDto(teamName, List.of());
        }

        int activeEmployees = employeeRepo.findByTeamNameAndActiveTrue(teamName).size();

        double capacityPercent = (activeEmployees * 100.0) / totalHeadcount;

        List<LocalDate> riskDates = new ArrayList<>();

        if (capacityPercent < config.getMinCapacityPercent()) {
            for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
                riskDates.add(d);
            }
        }

        return new CapacityAnalysisResultDto(teamName, riskDates);
    }
}
