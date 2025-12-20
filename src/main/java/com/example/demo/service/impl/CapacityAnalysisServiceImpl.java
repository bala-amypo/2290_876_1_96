package com.example.demo.service.impl;

import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.repository.CapacityAlertRepository;

import java.util.HashMap;          
import java.util.Map;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    
    private final TeamCapacityConfigRepository teamCapacityConfigRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final CapacityAlertRepository capacityAlertRepository;

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


    private final TeamCapacityConfigRepository configRepo;

    public CapacityAnalysisServiceImpl(TeamCapacityConfigRepository configRepo) {
        this.configRepo = configRepo;
    }

    @Override
    public List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date is invalid");
        }

        TeamCapacityConfig config = configRepo.findByTeamName(teamName)
                .orElseThrow(() -> new RuntimeException("Capacity config not found"));

        List<LocalDate> dates = new ArrayList<>();
        LocalDate d = start;
        while (!d.isAfter(end)) {
            dates.add(d);
            d = d.plusDays(1);
        }
        return dates;
    }

    @Override
    public void analyzeTeamCapacity(
            String teamName,
            LocalDate start,
            LocalDate end) {
        getOverlappingDates(teamName, start, end);
    }
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
@Override
public CapacityAnalysisResultDto analyzeTeamCapacity(
        String teamName,
        LocalDate start,
        LocalDate end
) {
    return new CapacityAnalysisResultDto(false, new HashMap<>());
}


}
