package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repo;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repo) {
        this.repo = repo;
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile updated) {
        EmployeeProfile existing = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        existing.setName(updated.getName());
        existing.setTeamName(updated.getTeamName());
        existing.setActive(updated.isActive());

        return repo.save(existing);
    }

    @Override
    public EmployeeProfile getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfile> getEmployeesByTeam(String teamName) {
        return repo.findByTeamNameAndActiveTrue(teamName);
    }
}
