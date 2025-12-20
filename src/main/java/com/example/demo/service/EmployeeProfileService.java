package com.example.demo.service;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.model.EmployeeProfile;

import java.util.List;

public interface EmployeeProfileService {

    EmployeeProfile create(EmployeeProfileDto dto);

    EmployeeProfile update(Long id, EmployeeProfileDto dto);

    void deactivate(Long id);

    boolean isActive(Long id);

    EmployeeProfile getById(Long id);

    List<EmployeeProfile> getByTeam(String team);

    List<EmployeeProfile> getAll();
}
