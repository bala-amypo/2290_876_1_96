package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployee(EmployeeProfile employee);

    @Query("""
        SELECT lr FROM LeaveRequest lr
        WHERE lr.status = 'APPROVED'
        AND lr.employee.teamName = :teamName
        AND lr.startDate <= :end
        AND lr.endDate >= :start
    """)
    List<LeaveRequest> findApprovedOverlappingForTeam(
            @Param("teamName") String teamName,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // ✅ REQUIRED BY TEST
    List<LeaveRequest> findApprovedOnDate(LocalDate date);
}
