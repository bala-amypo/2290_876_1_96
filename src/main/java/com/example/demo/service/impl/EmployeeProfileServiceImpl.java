package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    // ✅ Create Employee
    @Override
    public EmployeeProfileDto create(EmployeeProfileDto dto) {

        EmployeeProfile employee = new EmployeeProfile();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setTeamName(dto.getTeamName());
        employee.setRole(dto.getRole());
        employee.setActive(true); // default true (as per rules)

        // ❌ DO NOT set createdAt (entity constructor already handles it)

        EmployeeProfile saved = repository.save(employee);
        return mapToDto(saved);
    }

    // ✅ Update Employee
    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {

        EmployeeProfile employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setTeamName(dto.getTeamName());
        employee.setRole(dto.getRole());
        employee.setActive(dto.getActive());

        EmployeeProfile updated = repository.save(employee);
        return mapToDto(updated);
    }

    // ✅ Get Employee by ID
    @Override
    public EmployeeProfileDto getById(Long id) {
        EmployeeProfile employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return mapToDto(employee);
    }

    // ✅ Get Employees by Team
    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repository.findByTeamName(teamName)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 🔁 Entity → DTO Mapper
    private EmployeeProfileDto mapToDto(EmployeeProfile employee) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(employee.getId());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setTeamName(employee.getTeamName());
        dto.setRole(employee.getRole());
        dto.setActive(employee.getActive());
        return dto;
    }
}
