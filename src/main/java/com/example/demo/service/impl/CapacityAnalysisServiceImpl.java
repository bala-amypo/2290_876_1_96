package com.example.demo.service.impl;
import java.time.LocalDate;
import java.util.*;

import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import com.example.demo.service.CapacityAnalysisService;

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
public List<LocalDate> getOverlappingDates(
        String teamName,
        LocalDate start,
        LocalDate end) {

    List<LeaveRequest> leaves =
            leaveRequestService.getOverlappingForTeam(teamName, start, end);

    Set<LocalDate> overlappingDates = new HashSet<>();

    for (LeaveRequest leave : leaves) {
        LocalDate current = leave.getStartDate();
        while (!current.isAfter(leave.getEndDate())) {
            overlappingDates.add(current);
            current = current.plusDays(1);
        }
    }

    return new ArrayList<>(overlappingDates);
}

}
