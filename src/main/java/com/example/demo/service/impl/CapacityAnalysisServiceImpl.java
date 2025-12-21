package com.example.demo.service.impl;

import com.example.demo.model.LeaveRequest;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final TeamCapacityConfigRepository configRepository;

    public CapacityAnalysisServiceImpl(
            LeaveRequestRepository leaveRequestRepository,
            TeamCapacityConfigRepository configRepository
    ) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.configRepository = configRepository;
    }

    @Override
    public List<LocalDate> analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {

        TeamCapacityConfig config = configRepository
                .findByTeamName(teamName)
                .orElseThrow(() ->
                        new IllegalArgumentException("Team config not found"));

        int totalHeadcount = config.getTotalHeadcount();
        int minCapacityPercent = config.getMinCapacityPercent();

        int minAvailableEmployees =
                (int) Math.ceil(totalHeadcount * (minCapacityPercent / 100.0));

        List<LeaveRequest> leaves =
                leaveRequestRepository.findApprovedOverlappingForTeam(
                        teamName, startDate, endDate);

        Map<LocalDate, Integer> leaveCountByDate = new HashMap<>();

        for (LeaveRequest leave : leaves) {
            LocalDate date = leave.getStartDate();
            while (!date.isAfter(leave.getEndDate())) {
                leaveCountByDate.put(
                        date,
                        leaveCountByDate.getOrDefault(date, 0) + 1
                );
                date = date.plusDays(1);
            }
        }

        List<LocalDate> insufficientCapacityDates = new ArrayList<>();

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            int onLeave = leaveCountByDate.getOrDefault(date, 0);
            int available = totalHeadcount - onLeave;

            if (available < minAvailableEmployees) {
                insufficientCapacityDates.add(date);
            }
            date = date.plusDays(1);
        }

        return insufficientCapacityDates;
    }
}
