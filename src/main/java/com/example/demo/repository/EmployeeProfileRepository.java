package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;

import java.util.List;
import java.util.Optional;

public interface EmployeeProfileRepository {

    EmployeeProfile save(EmployeeProfile employee);

    Optional<EmployeeProfile> findById(Long id);

    List<EmployeeProfile> findByTeamNameAndActiveTrue(String teamName);

    List<EmployeeProfile> findAll();
}
