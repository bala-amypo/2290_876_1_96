package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // REQUIRED by tests
    List<LeaveRequest> findByEmployee(EmployeeProfile employee);

    // REQUIRED by capacity analysis
    List<LeaveRequest> getOverlappingForTeam(
            String teamName, LocalDate start, LocalDate end);
}
