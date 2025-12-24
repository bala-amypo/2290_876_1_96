package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repo;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repo) {
        this.repo = repo;
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile emp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        emp.setName(dto.getName());
        emp.setTeamName(dto.getTeamName());
        emp.setActive(dto.isActive());

        return toDto(repo.save(emp));
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repo.findByTeamNameAndActiveTrue(teamName)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile emp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        emp.setActive(false);
        repo.save(emp);
    }

    private EmployeeProfileDto toDto(EmployeeProfile e) {
        return new EmployeeProfileDto(
                e.getId(),
                e.getName(),
                e.getTeamName(),
                e.isActive()
        );
    }
}
