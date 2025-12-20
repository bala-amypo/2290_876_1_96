package com.example.demo.service;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.LeaveRequest;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.LeaveRequest;

public interface LeaveRequestService {
    LeaveRequest applyLeave(LeaveRequest request);
    LeaveRequest approveLeave(Long leaveId);
}

