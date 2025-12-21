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
import java.util.stream.Collectors;

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

        EmployeeProfile employee = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with id: " + dto.getEmployeeId()
                        ));

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(employee);
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setType(dto.getType());
        leave.setStatus(dto.getStatus());
        leave.setReason(dto.getReason());

        return toDto(leaveRepo.save(leave));
    }

    @Override
    public LeaveRequestDto approve(Long id) {
        LeaveRequest leave = leaveRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave not found"));

        leave.setStatus("APPROVED");
        return toDto(leaveRepo.save(leave));
    }

    @Override
    public LeaveRequestDto reject(Long id) {
        LeaveRequest leave = leaveRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave not found"));

        leave.setStatus("REJECTED");
        return toDto(leaveRepo.save(leave));
    }

    @Override
    public List<LeaveRequestDto> getByEmployee(Long id) {
        EmployeeProfile employee = employeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        return leaveRepo.findByEmployee(employee)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ MISSING METHOD — REQUIRED BY INTERFACE
    @Override
    public List<LeaveRequest> getOverlappingForTeam(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return leaveRepo.findApprovedOverlappingForTeam(
                teamName, startDate, endDate
        );
    }

    private LeaveRequestDto toDto(LeaveRequest leave) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(leave.getId());
        dto.setEmployeeId(leave.getEmployee().getId());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setType(leave.getType());
        dto.setStatus(leave.getStatus());
        dto.setReason(leave.getReason());
        return dto;
    }
    @Override
    public List<LeaveRequestDto> getOverlappingForTeam(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return leaveRepo
                .findApprovedOverlappingForTeam(teamName, startDate, endDate)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
