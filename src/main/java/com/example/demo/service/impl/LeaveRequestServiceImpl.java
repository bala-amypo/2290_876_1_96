package com.example.demo.service;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository repo;

    public LeaveRequestServiceImpl(LeaveRequestRepository repo) {
        this.repo = repo;
    }

    public LeaveRequestDto create(LeaveRequestDto dto) {
        return dto;
    }

    public void approve(Long id) {
        LeaveRequest lr = repo.findById(id).orElseThrow();
        lr.setStatus("APPROVED");
        repo.save(lr);
    }

    public void reject(Long id) {
        LeaveRequest lr = repo.findById(id).orElseThrow();
        lr.setStatus("REJECTED");
        repo.save(lr);
    }

    public java.util.List<LeaveRequestDto> getByEmployee(Long employeeId) {
        return java.util.List.of();
    }
}
