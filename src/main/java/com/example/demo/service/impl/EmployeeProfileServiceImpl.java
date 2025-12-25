package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import com.example.demo.exception.ResourceNotFoundException;

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
        EmployeeProfile emp = new EmployeeProfile();
        emp.setEmployeeId(dto.getEmployeeId());
        emp.setFullName(dto.getFullName());
        emp.setEmail(dto.getEmail());
        emp.setTeamName(dto.getTeamName());
        emp.setRole(dto.getRole());
        repo.save(emp);
        return toDto(emp);
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile emp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        emp.setFullName(dto.getFullName());
        emp.setEmail(dto.getEmail());
        emp.setTeamName(dto.getTeamName());
        emp.setRole(dto.getRole());
        return toDto(repo.save(emp));
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        return toDto(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repo.findByTeamName(teamName)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProfileDto> getAll() {
        return repo.findAll()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile emp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        emp.deactivate();
        repo.save(emp);
    }

    private EmployeeProfileDto toDto(EmployeeProfile e) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setEmployeeId(e.getEmployeeId());
        dto.setFullName(e.getFullName());
        dto.setEmail(e.getEmail());
        dto.setTeamName(e.getTeamName());
        dto.setRole(e.getRole());
        return dto;
    }
}
