package com.example.demo.service.impl;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRepo;
    private final EmployeeProfileRepository employeeRepo;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRepo,
                                   EmployeeProfileRepository employeeRepo) {
        this.leaveRepo = leaveRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public LeaveRequest create(LeaveRequest request) {

        if (request.getStartDate() == null || request.getEndDate() == null ||
                request.getStartDate().isAfter(request.getEndDate())) {
            throw new BadRequestException("Invalid date range");
        }

        EmployeeProfile employee = employeeRepo.findById(request.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        request.setEmployee(employee);
        request.setApproved(false);
        return leaveRepo.save(request);
    }

    @Override
    public LeaveRequest approve(Long id) {
        LeaveRequest leave = leaveRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        leave.setApproved(true);
        return leaveRepo.save(leave);
    }

    @Override
    public LeaveRequest reject(Long id) {
        LeaveRequest leave = leaveRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        leave.setApproved(false);
        return leaveRepo.save(leave);
    }
    @Override
public List<LeaveRequest> getOverlappingForTeam(
        String team, LocalDate start, LocalDate end) {
    return leaveRepo.findOverlappingLeavesForTeam(team, start, end);
}

}
