package com.example.demo.service;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.model.EmployeeProfile;

import java.util.List;

public interface EmployeeProfileService {

    EmployeeProfile create(EmployeeProfileDto dto);

    EmployeeProfile update(Long id, EmployeeProfileDto dto);

    void delete(Long id);

    List<EmployeeProfile> getAll();

    List<EmployeeProfile> getActiveEmployeesByTeam(String team);
}
