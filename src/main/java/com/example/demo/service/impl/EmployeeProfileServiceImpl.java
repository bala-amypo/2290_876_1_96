package com.example.demo.service;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repo;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repo) {
        this.repo = repo;
    }

    public EmployeeProfileDto create(EmployeeProfileDto dto) {
        EmployeeProfile e = new EmployeeProfile(
                null, dto.employeeId, dto.fullName,
                dto.email, dto.teamName, dto.role, true);
        repo.save(e);
        return dto;
    }

    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        return dto;
    }

    public void deactivate(Long id) {
        EmployeeProfile e = repo.findById(id).orElseThrow();
        e.setActive(false);
        repo.save(e);
    }

    public EmployeeProfileDto getById(Long id) {
        EmployeeProfile e = repo.findById(id).orElseThrow();
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.id = e.getId();
        dto.fullName = e.getFullName();
        return dto;
    }

    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repo.findByTeamNameAndActiveTrue(teamName)
                .stream().map(e -> new EmployeeProfileDto())
                .collect(Collectors.toList());
    }

    public List<EmployeeProfileDto> getAll() {
        return repo.findAll().stream()
                .map(e -> new EmployeeProfileDto())
                .collect(Collectors.toList());
    }
}
