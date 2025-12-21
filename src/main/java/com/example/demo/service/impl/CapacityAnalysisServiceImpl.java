package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.model.UserAccount;
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

    // ✅ CONSTRUCTOR REQUIRED BY TESTS
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

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String userEmail,
            LocalDate startDate,
            LocalDate endDate
    ) {

        // 🔹 Resolve user
        UserAccount user = userAccountRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        EmployeeProfile profile = user.getEmployeeProfile();
        if (profile == null) {
            throw new IllegalArgumentException("Employee profile missing");
        }

        String teamName = profile.getTeamName();

        TeamCapacityConfig config = configRepository
                .findByTeamName(teamName)
                .orElseThrow(() -> new IllegalArgumentException("Team config not found"));

        int totalHeadcount = config.getTotalHeadcount();
        int minAvailable =
                (int) Math.ceil(totalHeadcount * (config.getMinCapacityPercent() / 100.0));

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

        List<LocalDate> insufficientDates = new ArrayList<>();
        LocalDate d = startDate;

        while (!d.isAfter(endDate)) {
            int onLeave = leaveCount.getOrDefault(d, 0);
            if ((totalHeadcount - onLeave) < minAvailable) {
                insufficientDates.add(d);
            }
            d = d.plusDays(1);
        }

        return new CapacityAnalysisResultDto(insufficientDates);
    }
}
