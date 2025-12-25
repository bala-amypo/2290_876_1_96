package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import com.example.demo.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRepo;
    private final EmployeeProfileRepository empRepo;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRepo,
                                   EmployeeProfileRepository empRepo) {
        this.leaveRepo = leaveRepo;
        this.empRepo = empRepo;
    }

    @Override
    public LeaveRequestDto create(LeaveRequestDto dto) {
        EmployeeProfile emp = empRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(emp);
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setStatus("PENDING");
        leave.setType(dto.getType());
        leave.setReason(dto.getReason());

        return toDto(leaveRepo.save(leave));
    }

    @Override
    public LeaveRequestDto approve(Long id) {
        LeaveRequest leave = find(id);
        leave.setStatus("APPROVED");
        return toDto(leaveRepo.save(leave));
    }

    @Override
    public LeaveRequestDto reject(Long id) {
        LeaveRequest leave = find(id);
        leave.setStatus("REJECTED");
        return toDto(leaveRepo.save(leave));
    }

    @Override
    public List<LeaveRequestDto> getByEmployee(Long employeeId) {
        EmployeeProfile emp = empRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return leaveRepo.findByEmployee(emp)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDto> getOverlappingForTeam(String team, LocalDate start, LocalDate end) {
        return leaveRepo
                .findByEmployee_TeamNameAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        team, "APPROVED", end, start)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private LeaveRequest find(Long id) {
        return leaveRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found"));
    }

    private LeaveRequestDto toDto(LeaveRequest l) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(l.getId());
        dto.setEmployeeId(l.getEmployee().getId());
        dto.setStartDate(l.getStartDate());
        dto.setEndDate(l.getEndDate());
        dto.setStatus(l.getStatus());
        dto.setType(l.getType());
        dto.setReason(l.getReason());
        return dto;
    }
}
