package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    public LeaveRequestServiceImpl(
            LeaveRequestRepository leaveRequestRepository,
            EmployeeProfileRepository employeeProfileRepository
    ) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    @Override
    public LeaveRequestDto create(LeaveRequestDto dto) {

        EmployeeProfile employee = employeeProfileRepository
                .findById(dto.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Employee not found"));

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(employee);
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setType(dto.getType());
        leave.setStatus(dto.getStatus());
        leave.setReason(dto.getReason());

        LeaveRequest saved = leaveRequestRepository.save(leave);
        return toDto(saved);
    }

    @Override
    public LeaveRequestDto approve(Long id) {
        LeaveRequest leave = getLeave(id);
        leave.setStatus("APPROVED");
        return toDto(leaveRequestRepository.save(leave));
    }

    @Override
    public LeaveRequestDto reject(Long id) {
        LeaveRequest leave = getLeave(id);
        leave.setStatus("REJECTED");
        return toDto(leaveRequestRepository.save(leave));
    }

    @Override
    public List<LeaveRequestDto> getByEmployee(Long employeeId) {

        EmployeeProfile employee = employeeProfileRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Employee not found"));

        return leaveRequestRepository.findByEmployee(employee)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ===== Helpers =====

    private LeaveRequest getLeave(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Leave not found"));
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
}
