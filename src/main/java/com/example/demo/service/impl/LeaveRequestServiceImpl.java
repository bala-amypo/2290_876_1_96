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

    @Autowired
    private LeaveRequestRepository repository;

    @Override
    public LeaveRequest applyLeave(LeaveRequest lr) {
        lr.setStatus("PENDING");
        return repository.save(lr);
    }

    @Override
    public LeaveRequest approveLeave(Long id) {
        LeaveRequest lr = repository.findById(id).orElseThrow();
        lr.setStatus("APPROVED");
        return repository.save(lr);
    }

    @Override
    public List<LeaveRequest> getAll() {
        return repository.findAll();
    }
}
