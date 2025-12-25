package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
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
import java.util.List;
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
            String team,
            LocalDate start,
            LocalDate end
    ) {

        if (start.isAfter(end)) {
            throw new BadRequestException("Start date must be before end date");
        }

        TeamCapacityConfig config = configRepo.findByTeamName(team)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity config not found"));

        if (config.getTotalHeadcount() <= 0) {
            throw new BadRequestException("Invalid total headcount");
        }

        int total = config.getTotalHeadcount();
        Map<LocalDate, Integer> result = new HashMap<>();
        boolean risky = false;

        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {

            List<?> leaves = leaveRepo.findApprovedOverlappingForTeam(team, d, d);
            int available = total - leaves.size();
            int percent = (available * 100) / total;

            result.put(d, percent);

            if (percent < config.getMinCapacityPercent()) {
                risky = true;
                alertRepo.save(new CapacityAlert(team, d, "LOW", "Capacity below threshold"));
            }
        }

        return new CapacityAnalysisResultDto(risky, result);
    }
}
