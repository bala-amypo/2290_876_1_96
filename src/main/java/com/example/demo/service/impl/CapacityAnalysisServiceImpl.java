package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.CapacityAlert;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository configRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final CapacityAlertRepository alertRepo;

    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository configRepo,
            EmployeeProfileRepository employeeRepo,
            CapacityAlertRepository alertRepo
    ) {
        this.configRepo = configRepo;
        this.employeeRepo = employeeRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new BadRequestException("Invalid date range");
        }

        TeamCapacityConfig config = configRepo.findByTeamName(teamName)
                .orElseThrow(() -> new BadRequestException("Capacity config not found"));

        int headcount = employeeRepo.findByTeamName(teamName).size();
        List<LocalDate> lowCapacityDates = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

            double capacityPercent =
                    (headcount * 100.0) / config.getTotalHeadcount();

            // ✅ BOOLEAN condition — FIXED
            if (capacityPercent < config.getMinCapacityPercent()) {

                CapacityAlert alert = new CapacityAlert(
                        teamName,
                        date,
                        "LOW",
                        "Capacity below threshold"
                );

                alertRepo.save(alert);
                lowCapacityDates.add(date);
            }
        }

        return new CapacityAnalysisResultDto(teamName, lowCapacityDates);
    }

    @Override
    public List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
            dates.add(d);
        }
        return dates;
    }
}
