package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid Date Range: start date"
        );
    }

        EmployeeProfile emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Employee not found"
        ));


        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(emp);
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setType(dto.getType());
        leave.setReason(dto.getReason());
        leave.setStatus("PENDING");

        return toDto(leaveRepo.save(leave));
    }

    @Override
    public LeaveRequestDto approve(Long id) {
    LeaveRequest leave = leaveRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Leave request not found"
            ));
    leave.setStatus("APPROVED");
    return toDto(leaveRepo.save(leave));
}


    @Override
public LeaveRequestDto reject(Long id) {
    LeaveRequest leave = leaveRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Leave request not found"
            ));
    leave.setStatus("REJECTED");
    return toDto(leaveRepo.save(leave));
}



    @Override
    public List<LeaveRequestDto> getByEmployee(Long employeeId) {
        EmployeeProfile emp = employeeRepo.findById(employeeId).orElseThrow();
        return leaveRepo.findByEmployee(emp)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDto> getOverlappingForTeam(
            String teamName, LocalDate start, LocalDate end) {

        return leaveRepo.findApprovedOverlappingForTeam(teamName, start, end)
                .stream().map(this::toDto).collect(Collectors.toList());
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
