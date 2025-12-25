package com.example.demo.repository;

import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query("""
        SELECT COUNT(l)
        FROM LeaveRequest l
        WHERE l.employee.teamName = :teamName
          AND l.status = 'APPROVED'
          AND :date BETWEEN l.startDate AND l.endDate
    """)
    int countApprovedLeavesOnDate(
            @Param("teamName") String teamName,
            @Param("date") LocalDate date
    );
}
