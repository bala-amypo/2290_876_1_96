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

    @Override
    public EmployeeProfile create(EmployeeProfileDto dto) {
        EmployeeProfile emp = new EmployeeProfile();
        emp.setName(dto.getName());
        emp.setTeam(dto.getTeam());
        emp.setActive(true);
        return repository.save(emp);
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile emp = repository.findById(id).orElseThrow();
        emp.setName(dto.getName());
        emp.setTeam(dto.getTeam());
        return repository.save(emp);
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile emp = repository.findById(id).orElseThrow();
        emp.setActive(false);
        repository.save(emp);
    }

    @Override
    public boolean isActive(Long id) {
        return repository.findById(id)
                .map(EmployeeProfile::isActive)
                .orElse(false);
    }

    @Override
    public EmployeeProfile getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<EmployeeProfile> getByTeam(String team) {
        return repository.findAll()
                .stream()
                .filter(e -> team.equals(e.getTeam()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProfile> getAll() {
        return repository.findAll();
    }
    @Override
public List<EmployeeProfile> getActiveEmployeesByTeam(String team) {
    return repository.findAll().stream()
            .filter(EmployeeProfile::isActive)
            .filter(e -> e.getTeam().equalsIgnoreCase(team))
            .toList();
}

}
