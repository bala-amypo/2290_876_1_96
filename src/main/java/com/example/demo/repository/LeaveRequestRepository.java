package com.example.demo.repository;

import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployee_Id(Long employeeId);

    List<LeaveRequest>
    findByEmployee_TeamNameAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String teamName,
            String status,
            LocalDate endDate,
            LocalDate startDate
    );
}
