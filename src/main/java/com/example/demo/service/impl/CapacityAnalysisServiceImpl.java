package com.example.demo.service.impl;

import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

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
}
