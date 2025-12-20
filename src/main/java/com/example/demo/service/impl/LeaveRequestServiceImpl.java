package com.example.demo.service.impl;

import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository repo;

    public LeaveRequestServiceImpl(LeaveRequestRepository repo) {
        this.repo = repo;
    }

    @Override
    public LeaveRequest applyLeave(LeaveRequest request) {
        request.setStatus("PENDING");
        return repo.save(request);
    }

    @Override
    public LeaveRequest approveLeave(Long id) {
        LeaveRequest req = repo.findById(id).orElseThrow();
        req.setStatus("APPROVED");
        return repo.save(req);
    }
}
