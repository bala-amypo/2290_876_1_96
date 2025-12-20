package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmployeeProfileServiceImpl
        implements EmployeeProfileService {

    @Autowired
    private EmployeeProfileRepository repository;

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
    public void delete(Long id) {
        repository.deleteById(id);
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
                .collect(Collectors.toList());
    }
}
