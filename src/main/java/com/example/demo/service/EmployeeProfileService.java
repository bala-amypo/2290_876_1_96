package com.example.demo.service;

import com.example.demo.model.EmployeeProfile;
import java.util.List;

public interface EmployeeProfileService {
    List<EmployeeProfile> getActiveEmployeesByTeam(String team);
}
