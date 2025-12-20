package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository repository;

    public LeaveRequestServiceImpl(LeaveRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public LeaveRequest create(LeaveRequestDto dto) {
        LeaveRequest lr = new LeaveRequest();
        lr.setEmployeeId(dto.getEmployeeId());
        lr.setStartDate(dto.getStartDate());
        lr.setEndDate(dto.getEndDate());
        lr.setStatus("PENDING");
        return repository.save(lr);
    }

    @Override
    public LeaveRequest approve(Long id) {
        LeaveRequest lr = repository.findById(id).orElseThrow();
        lr.setStatus("APPROVED");
        return repository.save(lr);
    }

    @Override
    public LeaveRequest reject(Long id) {
        LeaveRequest lr = repository.findById(id).orElseThrow();
        lr.setStatus("REJECTED");
        return repository.save(lr);
    }

    @Override
    public List<LeaveRequest> getByEmployee(Long employeeId) {
        return repository.findAll()
                .stream()
                .filter(l -> l.getEmployeeId().equals(employeeId))
                .toList();
    }

    @Override
    public List<LeaveRequest> getOverlappingForTeam(
            String team,
            LocalDate start,
            LocalDate end) {

        return repository
                .findByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        "APPROVED", end, start);
    }
}
