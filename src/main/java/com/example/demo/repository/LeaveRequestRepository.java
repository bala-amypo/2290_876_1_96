package com.example.demo.repository;

import com.example.demo.model.LeaveRequest;

import java.util.List;
import java.util.Optional;

public interface LeaveRequestRepository {

    LeaveRequest save(LeaveRequest leave);

    Optional<LeaveRequest> findById(Long id);

    List<LeaveRequest> findByEmployeeId(Long employeeId);
}
