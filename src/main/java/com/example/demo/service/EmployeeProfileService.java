package com.example.demo.service;

import com.example.demo.model.EmployeeProfile;
import java.util.List;

public interface EmployeeProfileService {

    EmployeeProfile create(EmployeeProfileDto dto);

    EmployeeProfile update(long id, EmployeeProfileDto dto);

    void deactivate(long id);

    boolean isActive(long id);

    EmployeeProfile getById(long id);

    EmployeeProfile getByTeam(String team);

    List<EmployeeProfile> getAll();
}
