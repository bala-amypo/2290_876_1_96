package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;

import java.util.Optional;

public interface EmployeeProfileRepository {

    Optional<EmployeeProfile> findById(Long id);
}
