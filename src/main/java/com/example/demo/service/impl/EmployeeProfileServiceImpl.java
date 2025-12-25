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
        EmployeeProfile e = new EmployeeProfile();
        e.setName(dto.getName());
        e.setTeamName(dto.getTeamName());
        e.setActive(true);
        repo.save(e);
        return dto;
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        e.setName(dto.getName());
        e.setTeamName(dto.getTeamName());
        e.setActive(dto.isActive());

        repo.save(e);
        return dto;
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        EmployeeProfile e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setTeamName(e.getTeamName());
        dto.setActive(e.isActive());
        return dto;
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repo.findByTeamNameAndActiveTrue(teamName)
                .stream()
                .map(e -> {
                    EmployeeProfileDto dto = new EmployeeProfileDto();
                    dto.setId(e.getId());
                    dto.setName(e.getName());
                    dto.setTeamName(e.getTeamName());
                    dto.setActive(e.isActive());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<EmployeeProfileDto> getAll() {
        return repo.findAll().stream()
                .map(e -> {
                    EmployeeProfileDto dto = new EmployeeProfileDto();
                    dto.setId(e.getId());
                    dto.setName(e.getName());
                    dto.setTeamName(e.getTeamName());
                    dto.setActive(e.isActive());
                    return dto;
                })
                .toList();
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        e.setActive(false);
        repo.save(e);
    }
}
