package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRepo;
    private final EmployeeProfileRepository employeeRepo;

    public LeaveRequestServiceImpl(
            LeaveRequestRepository leaveRepo,
            EmployeeProfileRepository employeeRepo) {
        this.leaveRepo = leaveRepo;
        this.employeeRepo = employeeRepo;
    }
    @Override
public LeaveRequestDto create(LeaveRequestDto dto) {

    if (dto.getStartDate().isAfter(dto.getEndDate())) {
        throw new RuntimeException("Invalid date range");
    }

    EmployeeProfile employee = employeeRepo.findById(dto.getEmployeeId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    LeaveRequest leave = new LeaveRequest();
    leave.setEmployee(employee);
    leave.setStartDate(dto.getStartDate());
    leave.setEndDate(dto.getEndDate());
    leave.setType(dto.getType());
    leave.setReason(dto.getReason());
    leave.setStatus("PENDING");

    LeaveRequest saved = leaveRepo.save(leave);

    LeaveRequestDto result = new LeaveRequestDto();
    result.setEmployeeId(saved.getEmployee().getId());
    result.setStartDate(saved.getStartDate());
    result.setEndDate(saved.getEndDate());
    result.setType(saved.getType());
    result.setReason(saved.getReason());

    return result;
}

   
}
