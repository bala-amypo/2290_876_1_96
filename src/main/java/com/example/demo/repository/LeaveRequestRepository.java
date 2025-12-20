package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Get all leaves of one employee
    List<LeaveRequest> findByEmployee(EmployeeProfile employee);

    // Get APPROVED overlapping leaves for a team
    List<LeaveRequest> findByEmployee_TeamNameAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String teamName,
            String status,
            LocalDate endDate,
            LocalDate startDate
    );
}
