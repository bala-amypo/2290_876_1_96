package com.example.demo.repository;

import com.example.demo.model.LeaveRequest;

import java.util.List;

public interface LeaveRequestRepository {

    LeaveRequest save(LeaveRequest leave);

    List<LeaveRequest> findByEmployeeId(Long employeeId);
}
