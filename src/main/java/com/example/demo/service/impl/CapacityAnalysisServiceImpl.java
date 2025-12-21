package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.service.CapacityAnalysisService;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.model.LeaveRequest;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.*;

import java.time.LocalDate;
import java.util.*;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository configRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final LeaveRequestService leaveRequestService;
    private final CapacityAlertRepository alertRepo;

    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository configRepo,
            EmployeeProfileRepository employeeRepo,
            LeaveRequestService leaveRequestService,
            CapacityAlertRepository alertRepo
    ) {
        this.configRepo = configRepo;
        this.employeeRepo = employeeRepo;
        this.leaveRequestService = leaveRequestService;
        this.alertRepo = alertRepo;
    }

    @Override
    public List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end
    ) {

        List<LeaveRequestDto> leaves =
                leaveRequestService.getOverlappingForTeam(teamName, start, end);

        List<LocalDate> result = new ArrayList<>();

        for (LeaveRequestDto dto : leaves) {
            LocalDate current = dto.getStartDate();
            while (!current.isAfter(dto.getEndDate())) {
                if (!current.isBefore(start) && !current.isAfter(end)) {
                    if (!result.contains(current)) {
                        result.add(current);
                    }
                }
                current = current.plusDays(1);
            }
        }

        return result;
    }
    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {

        TeamCapacityConfig config = configRepo
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
