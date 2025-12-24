package com.example.demo.service.impl;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeProfile create(EmployeeProfile employee) {
        return repository.save(employee);
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile employee) {
        EmployeeProfile existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employee.setId(existing.getId());
        return repository.save(employee);
    }

    @Override
    public EmployeeProfile getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfile> getByTeam(String teamName) {
        return repository.findByTeamName(teamName);
    }

    @Override
    public List<EmployeeProfile> getAll() {
        return repository.findAll();
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employee.setActive(false);
        repository.save(employee);
    }
}
