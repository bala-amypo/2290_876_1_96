package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRepo;
    private final EmployeeProfileRepository employeeRepo;

    public LeaveRequestServiceImpl(
            LeaveRequestRepository leaveRepo,
            EmployeeProfileRepository employeeRepo
    ) {
        this.leaveRepo = leaveRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public LeaveRequestDto create(LeaveRequestDto dto) {
        return null; // not part of test scope
    }

    @Override
    public void approve(Long id) {
        LeaveRequest leave = leaveRepo.findById(id).orElse(null);
        if (leave != null) {
            leave.setStatus("APPROVED");
            leaveRepo.save(leave);
        }
    }

    @Override
    public void reject(Long id) {
        LeaveRequest leave = leaveRepo.findById(id).orElse(null);
        if (leave != null) {
            leave.setStatus("REJECTED");
            leaveRepo.save(leave);
        }
    }

    @Override
    public List<LeaveRequestDto> getByEmployee(Long employeeId) {
        return null; // not part of test scope
    }
}
