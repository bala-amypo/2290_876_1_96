package com.example.demo.service;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.dto.EmployeeProfileDto;

import java.util.List;

public interface EmployeeProfileService {

    EmployeeProfile create(EmployeeProfileDto dto);

    EmployeeProfile update(Long id, EmployeeProfileDto dto);

    EmployeeProfile getById(Long id);

    List<EmployeeProfile> getByTeam(String teamName);
}
