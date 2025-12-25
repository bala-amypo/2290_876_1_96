package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployee(EmployeeProfile employee);

    List<LeaveRequest> findByEmployee_TeamNameAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String teamName,
            String status,
            LocalDate end,
            LocalDate start
    );

    long countByEmployee_TeamNameAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String teamName,
            String status,
            LocalDate date,
            LocalDate date2
    );
}
