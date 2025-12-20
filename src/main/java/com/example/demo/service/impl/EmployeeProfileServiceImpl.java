package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProfileServiceImpl {

    private final EmployeeProfileRepository repo;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repo) {
        this.repo = repo;
    }

    public EmployeeProfile create(EmployeeProfileDto dto) {

        EmployeeProfile e = new EmployeeProfile(
                null,
                dto.getEmployeeId(),
                dto.getFullName(),
                dto.getEmail(),
                dto.getTeamName(),
                dto.getRole(),
                true
        );

        return repo.save(e);
    }

    public EmployeeProfile update(EmployeeProfileDto dto) {

        EmployeeProfile e = repo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Not found"));

        e.setFullName(dto.getFullName());
        e.setActive(true);

        return repo.save(e);
    }
}
