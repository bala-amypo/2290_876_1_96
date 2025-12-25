package com.example.demo.repository;

import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // ✅ COUNT approved leaves for a team on a specific date
    @Query("""
        SELECT COUNT(lr)
        FROM LeaveRequest lr
        WHERE lr.status = 'APPROVED'
          AND lr.employee.teamName = :teamName
          AND lr.startDate <= :date
          AND lr.endDate >= :date
    """)
    long countApprovedLeavesOnDate(
            @Param("teamName") String teamName,
            @Param("date") LocalDate date
    );
}
