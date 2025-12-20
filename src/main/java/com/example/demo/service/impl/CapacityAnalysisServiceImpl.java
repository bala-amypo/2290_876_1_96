package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.CapacityAlert;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository capacityRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final LeaveRequestRepository leaveRepo;
    private final CapacityAlertRepository alertRepo;

    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository capacityRepo,
            EmployeeProfileRepository employeeRepo,
            LeaveRequestRepository leaveRepo,
            CapacityAlertRepository alertRepo
    ) {
        this.capacityRepo = capacityRepo;
        this.employeeRepo = employeeRepo;
        this.leaveRepo = leaveRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName, LocalDate start, LocalDate end) {

        if (start.isAfter(end)) {
            throw new BadRequestException("Start date is invalid");
        }

        TeamCapacityConfig config = capacityRepo.findByTeamName(teamName)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity config not found"));

        List<EmployeeProfile> employees =
                employeeRepo.findByTeamNameAndActiveTrue(teamName);

        List<LeaveRequest> leaves =
                leaveRepo.findApprovedOverlappingForTeam(teamName, start, end);

        Map<LocalDate, Double> capacityMap = new HashMap<>();
        boolean risky = false;

        LocalDate date = start;
        while (!date.isAfter(end)) {

            long onLeave = leaves.stream()
                    .filter(l -> !date.isBefore(l.getStartDate())
                              && !date.isAfter(l.getEndDate()))
                    .count();

            double capacity =
                    ((double) (config.getTotalHeadcount() - onLeave)
                            / config.getTotalHeadcount()) * 100;

            capacityMap.put(date, capacity);

            if (capacity < config.getMinCapacityPercent()) {
                risky = true;

                CapacityAlert alert = new CapacityAlert();
                alert.setTeamName(teamName);
                alert.setDate(date);
                alert.setSeverity("HIGH");
                alert.setMessage("Team capacity below threshold");
                alertRepo.save(alert);
            }

            date = date.plusDays(1);
        }

        CapacityAnalysisResultDto dto = new CapacityAnalysisResultDto();
        dto.setRisky(risky);
        dto.setCapacityByDate(capacityMap);
        return dto;
    }
}
