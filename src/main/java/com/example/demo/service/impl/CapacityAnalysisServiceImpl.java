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

    private final LeaveRequestRepository leaveRepo;

    public CapacityAnalysisServiceImpl(LeaveRequestRepository leaveRepo) {
        this.leaveRepo = leaveRepo;
    }

    @Override
    public List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end
    ) {
        List<LeaveRequest> leaves =
                leaveRepo.findApprovedOverlappingForTeam(teamName, start, end);

        List<LocalDate> dates = new ArrayList<>();

        for (LeaveRequest leave : leaves) {
            LocalDate d = leave.getStartDate();
            while (!d.isAfter(leave.getEndDate())) {
                dates.add(d);
                d = d.plusDays(1);
            }
        }
        return dates;
    }
}
