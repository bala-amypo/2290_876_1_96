package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository teamCapacityConfigRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final CapacityAlertRepository capacityAlertRepository;

    // ✅ SINGLE constructor (fixed)
    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository teamCapacityConfigRepository,
            EmployeeProfileRepository employeeProfileRepository,
            LeaveRequestRepository leaveRequestRepository,
            CapacityAlertRepository capacityAlertRepository
    ) {
        this.teamCapacityConfigRepository = teamCapacityConfigRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.capacityAlertRepository = capacityAlertRepository;
    }

    // ✅ helper method (kept)
    public List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end
    ) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date is invalid");
        }

        TeamCapacityConfig config = teamCapacityConfigRepository
                .findByTeamName(teamName)
                .orElseThrow(() -> new RuntimeException("Capacity config not found"));

        List<LocalDate> dates = new ArrayList<>();
        LocalDate d = start;
        while (!d.isAfter(end)) {
            dates.add(d);
            d = d.plusDays(1);
        }
        return dates;
    }

    // ✅ CORRECT override (matches interface)
    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate start,
            LocalDate end
    ) {
        // Minimal logic for now
        getOverlappingDates(teamName, start, end);

        return new CapacityAnalysisResultDto(false, new HashMap<>());
    }
}
