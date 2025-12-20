package com.example.demo.service.impl;


import com.example.demo.service.CapacityAnalysisService;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;
import com.example.demo.dto.LeaveRequestDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final LeaveRequestService leaveRequestService;

    public CapacityAnalysisServiceImpl(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @Override
    public List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end) {

        List<LeaveRequestDto> leaves =
                leaveRequestService.getOverlappingForTeam(teamName, start, end);

        Set<LocalDate> dates = new HashSet<>();

        for (LeaveRequest leave : leaves) {
            LocalDate d = leave.getStartDate();
            while (!d.isAfter(leave.getEndDate())) {
                dates.add(d);
                d = d.plusDays(1);
            }
        }

        return new ArrayList<>(dates);
    }
}
