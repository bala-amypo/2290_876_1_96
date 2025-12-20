package com.example.demo.service.impl;

import com.example.demo.model.CapacityAlert;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    @Autowired
    private EmployeeProfileRepository employeeRepository;

    @Autowired
    private TeamCapacityConfigRepository configRepository;

    @Override
    public List<CapacityAlert> analyzeCapacity(String team,
                                                LocalDate startDate,
                                                LocalDate endDate) {

        TeamCapacityConfig config =
                configRepository.findByTeam(team);

        List<EmployeeProfile> employees =
                employeeRepository.findAll().stream()
                        .filter(e -> e.getTeam().equalsIgnoreCase(team))
                        .filter(EmployeeProfile::isActive)
                        .collect(Collectors.toList());   // ✅ FIXED

        int totalEmployees = employees.size();
        int maxAllowed =
                (int) Math.ceil(totalEmployees * config.getMaxPercentage() / 100.0);

        List<CapacityAlert> alerts = new ArrayList<>();

        if (totalEmployees > maxAllowed) {
            CapacityAlert alert = new CapacityAlert();
            alert.setTeam(team);
            alert.setMessage("Team capacity exceeded");
            alerts.add(alert);
        }

        return alerts;
    }
}
