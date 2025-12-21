package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
}
