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
    public EmployeeProfileDto create(EmployeeProfileDto dto) {
        EmployeeProfile employee = new EmployeeProfile();
        employee.setEmail(dto.getEmail());
        employee.setTeamName(dto.getTeamName());
        employee.setActive(true);

        return mapToDto(repo.save(employee));
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile employee = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setTeamName(dto.getTeamName());
        return mapToDto(repo.save(employee));
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        return repo.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repo.findByTeamNameAndActiveTrue(teamName)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProfileDto> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile employee = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setActive(false);
        repo.save(employee);
    }

    private EmployeeProfileDto mapToDto(EmployeeProfile employee) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(employee.getId());
        dto.setEmail(employee.getEmail());
        dto.setTeamName(employee.getTeamName());
        return dto;
    }
}
