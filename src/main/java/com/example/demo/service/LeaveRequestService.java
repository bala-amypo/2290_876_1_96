package com.example.demo.service;

import com.example.demo.model.LeaveRequest;

public interface LeaveRequestService {
    LeaveRequest applyLeave(LeaveRequest request);
    LeaveRequest approveLeave(Long id);
}
