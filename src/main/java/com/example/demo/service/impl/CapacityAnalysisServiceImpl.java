package com.example.demo.service.impl;

import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final LeaveRequestRepository leaveRequestRepository;

    public CapacityAnalysisServiceImpl(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @Override
    public List<LocalDate> analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {

        List<LeaveRequest> leaves =
                leaveRequestRepository.findApprovedOverlappingForTeam(
                        teamName, startDate, endDate);

        List<LocalDate> overlappingDates = new ArrayList<>();

        for (LeaveRequest leave : leaves) {
            LocalDate date = leave.getStartDate();
            while (!date.isAfter(leave.getEndDate())) {
                if (!overlappingDates.contains(date)) {
                    overlappingDates.add(date);
                }
                date = date.plusDays(1);
            }
        }

        return overlappingDates;
    }
}
