package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.LeaveRequest;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;

import java.time.LocalDate;

public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRepo;
    private final EmployeeProfileRepository employeeRepo;

    // ✅ Constructor EXACTLY as test expects
    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRepo,
                                   EmployeeProfileRepository employeeRepo) {
        this.leaveRepo = leaveRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public LeaveRequestDto createLeave(LeaveRequestDto dto) {

        EmployeeProfile emp =
                employeeRepo.findByEmployeeId(dto.getEmployeeId()).orElse(null);

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(emp);
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setType(dto.getType());
        leave.setReason(dto.getReason());
        leave.setStatus("PENDING");

        LeaveRequest saved = leaveRepo.save(leave);

        return new LeaveRequestDto(
                saved.getId(),
                emp.getEmployeeId(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getType(),
                saved.getReason(),
                saved.getStatus()
        );
    }
}
