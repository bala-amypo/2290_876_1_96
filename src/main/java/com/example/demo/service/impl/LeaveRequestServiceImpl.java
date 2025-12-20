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
    public LeaveRequest create(LeaveRequestDto dto) {

        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        EmployeeProfile employee = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveRequest leave = new LeaveRequest(
                employee,
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getType(),
                "PENDING",
                dto.getReason()
        );

        return leaveRepo.save(leave);
    }

    @Override
    public LeaveRequest approve(Long id) {
        LeaveRequest leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus("APPROVED");
        return leaveRepo.save(leave);
    }

    @Override
    public LeaveRequest reject(Long id) {
        LeaveRequest leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus("REJECTED");
        return leaveRepo.save(leave);
    }

    @Override
    public List<LeaveRequest> getByEmployee(Long employeeId) {
        EmployeeProfile employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return leaveRepo.findByEmployee(employee);
    }

    @Override
    public List<LeaveRequest> getOverlappingForTeam(
            String teamName, LocalDate start, LocalDate end) {

        return leaveRepo.getOverlappingForTeam(teamName, start, end);
    }
}
