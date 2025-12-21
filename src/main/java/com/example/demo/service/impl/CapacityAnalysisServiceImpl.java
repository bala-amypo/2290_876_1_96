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

    // ✅ REQUIRED BY TESTS
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

        // 1️⃣ Resolve user → employee → team
        UserAccount user = userAccountRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        EmployeeProfile profile = user.getEmployeeProfile();
        if (profile == null) {
            throw new IllegalArgumentException("Employee profile missing");
        }

        String teamName = profile.getTeamName();

        // 2️⃣ Load capacity config
        TeamCapacityConfig config = configRepository
                .findByTeamName(teamName)
                .orElseThrow(() -> new IllegalArgumentException("Team config not found"));

        int totalHeadcount = config.getTotalHeadcount();
        int minCapacityPercent = config.getMinCapacityPercent();

        // 3️⃣ Load approved leaves
        List<LeaveRequest> leaves =
                leaveRequestRepository.findApprovedOverlappingForTeam(
                        teamName, startDate, endDate);

        // 4️⃣ Count leaves per date
        Map<LocalDate, Integer> leaveCount = new HashMap<>();
        for (LeaveRequest leave : leaves) {
            LocalDate d = leave.getStartDate();
            while (!d.isAfter(leave.getEndDate())) {
                leaveCount.put(d, leaveCount.getOrDefault(d, 0) + 1);
                d = d.plusDays(1);
            }
        }

        // 5️⃣ Calculate capacity %
        Map<LocalDate, Double> capacityByDate = new LinkedHashMap<>();
        boolean risky = false;

        LocalDate d = startDate;
        while (!d.isAfter(endDate)) {
            int onLeave = leaveCount.getOrDefault(d, 0);
            int available = totalHeadcount - onLeave;

            double capacityPercent =
                    ((double) available / totalHeadcount) * 100;

            capacityByDate.put(d, capacityPercent);

            if (capacityPercent < minCapacityPercent) {
                risky = true;
            }

            d = d.plusDays(1);
        }

        // 6️⃣ Return EXACT DTO expected by tests
        return new CapacityAnalysisResultDto(risky, capacityByDate);
    }
}
