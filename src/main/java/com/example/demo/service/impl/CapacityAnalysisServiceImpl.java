package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.service.CapacityAnalysisService;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            LocalDate end
    ) {

        List<LeaveRequestDto> leaves =
                leaveRequestService.getOverlappingForTeam(teamName, start, end);

        List<LocalDate> result = new ArrayList<>();

        for (LeaveRequestDto leave : leaves) {
            LocalDate current = leave.getStartDate();
            while (!current.isAfter(leave.getEndDate())) {
                result.add(current);
                current = current.plusDays(1);
            }
        }

        return result; // ✅ List<LocalDate> EXACT MATCH
    }
}
