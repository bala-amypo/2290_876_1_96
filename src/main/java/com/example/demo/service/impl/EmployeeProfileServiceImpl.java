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

    private final EmployeeProfileRepository employeeRepo;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public EmployeeProfileDto create(EmployeeProfileDto dto) {
        EmployeeProfile employee = new EmployeeProfile();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setTeamName(dto.getTeamName());
        employee.setRole(dto.getRole());
        employee.setActive(true);

        EmployeeProfile saved = employeeRepo.save(employee);

        return mapToDto(saved);
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (dto.getFullName() != null) employee.setFullName(dto.getFullName());
        if (dto.getTeamName() != null) employee.setTeamName(dto.getTeamName());
        if (dto.getRole() != null) employee.setRole(dto.getRole());

        EmployeeProfile saved = employeeRepo.save(employee);
        return mapToDto(saved);
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        EmployeeProfile employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return mapToDto(employee);
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return employeeRepo.findByTeamNameAndActiveTrue(teamName)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProfileDto> getAll() {
        return employeeRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employee.setActive(false);
        employeeRepo.save(employee);
    }

    private EmployeeProfileDto mapToDto(EmployeeProfile employee) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(employee.getId()); // ⭐ FIX #1 & #3
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setTeamName(employee.getTeamName());
        dto.setRole(employee.getRole());
        return dto;
    }
}
