package com.example.demo.service.impl;

import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;

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
    public LeaveRequest create(LeaveRequestDto dto) { return null; }

    @Override
    public LeaveRequest approve(long id) { return null; }

    @Override
    public LeaveRequest reject(long id) { return null; }

    @Override
    public LeaveRequest getByEmployee(long empId) { return null; }

    @Override
    public List<LeaveRequest> findApprovedOnDate(LocalDate date) {
        return new ArrayList<>();
    }

    @Override
    public List<LeaveRequest> findOverlappingForTeam(
            String team, LocalDate start, LocalDate end) {
        return new ArrayList<>();
    }
}
