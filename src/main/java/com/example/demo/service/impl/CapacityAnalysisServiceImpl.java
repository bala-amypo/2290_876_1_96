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
import java.util.List;
import java.util.Map;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository configRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final LeaveRequestRepository leaveRepo;
    private final CapacityAlertRepository alertRepo;

    // ✅ EXACT constructor required by tests
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
        if (total == 0) {
            return new CapacityAnalysisResultDto(false, new HashMap<>());
        }

        Map<LocalDate, Integer> capacityByDate = new HashMap<>();
        boolean risky = false;

        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {

            int activeEmployees =
                    employeeRepo.findByTeamNameAndActiveTrue(teamName).size();

            int onLeave =
                    leaveRepo.countApprovedLeavesOnDate(teamName, d);

            int available = activeEmployees - onLeave;
            int percent = (available * 100) / total;

            capacityByDate.put(d, percent);

            if (percent < config.getMinCapacityPercent()) {
                risky = true;

                alertRepo.save(new CapacityAlert(
                        teamName,
                        d,
                        "LOW",
                        "Capacity below threshold"
                ));
            }
        }

        return new CapacityAnalysisResultDto(risky, capacityByDate);
    }
}
