package com.example.demo.service.impl;

import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository configRepo,
            EmployeeProfileRepository employeeRepo,
            LeaveRequestRepository leaveRepo,
            CapacityAlertRepository alertRepo) {
    }
    @Override
    public boolean analyzeTeamCapacity(
            String team, LocalDate start, LocalDate end) {
        return true;
    }

    @Override
    public void analyzeOverCapacity(
            String team, LocalDate start, LocalDate end) {
    }
}
