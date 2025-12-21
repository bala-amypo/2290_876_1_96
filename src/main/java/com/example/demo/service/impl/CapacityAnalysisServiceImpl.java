package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.model.LeaveRequest;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.*;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final TeamCapacityConfigRepository configRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final UserAccountRepository userAccountRepository;
    private final CapacityAlertRepository capacityAlertRepository;

    public CapacityAnalysisServiceImpl(
            LeaveRequestRepository leaveRequestRepository,
            TeamCapacityConfigRepository configRepository,
            EmployeeProfileRepository employeeProfileRepository,
            UserAccountRepository userAccountRepository,
            CapacityAlertRepository capacityAlertRepository
    ) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.configRepository = configRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.userAccountRepository = userAccountRepository;
        this.capacityAlertRepository = capacityAlertRepository;
    }

    public CapacityAnalysisServiceImpl(
            LeaveRequestRepository leaveRequestRepository,
            TeamCapacityConfigRepository configRepository,
            EmployeeProfileRepository employeeProfileRepository,
            UserAccountRepository userAccountRepository,
            CapacityAlertRepository capacityAlertRepository,
            CapacityAlertRepository ignoredDuplicate
    ) {
        this(
                leaveRequestRepository,
                configRepository,
                employeeProfileRepository,
                userAccountRepository,
                capacityAlertRepository
        );
    }

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {

        TeamCapacityConfig config = configRepository
                .findByTeamName(teamName)
                .orElseThrow(() ->
                        new IllegalArgumentException("Team config not found"));

        int total = config.getTotalHeadcount();
        int minRequired =
                (int) Math.ceil(total * (config.getMinCapacityPercent() / 100.0));

        List<LeaveRequest> leaves =
                leaveRequestRepository.findApprovedOverlappingForTeam(
                        teamName, startDate, endDate);

        Map<LocalDate, Integer> leaveCount = new HashMap<>();

        for (LeaveRequest leave : leaves) {
            LocalDate d = leave.getStartDate();
            while (!d.isAfter(leave.getEndDate())) {
                leaveCount.put(d, leaveCount.getOrDefault(d, 0) + 1);
                d = d.plusDays(1);
            }
        }

        boolean risky = false;
        Map<LocalDate, Double> capacityMap = new LinkedHashMap<>();

        LocalDate d = startDate;
        while (!d.isAfter(endDate)) {
            int onLeave = leaveCount.getOrDefault(d, 0);
            int available = total - onLeave;

            double capacityPercent = (available * 100.0) / total;
            capacityMap.put(d, capacityPercent);

            if (available < minRequired) {
                risky = true;
            }
            d = d.plusDays(1);
        }

        return new CapacityAnalysisResultDto(risky, capacityMap);
    }
}
