package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // ✅ USED BY getByEmployee()
    List<LeaveRequest> findByEmployee(EmployeeProfile employee);

    // ✅ USED BY getOverlappingForTeam()
    List<LeaveRequest>
    findByEmployee_TeamNameAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String teamName,
            String status,
            LocalDate end,
            LocalDate start
    );

    // ✅ USED BY CapacityAnalysisServiceImpl
    @Query("""
        SELECT COUNT(lr)
        FROM LeaveRequest lr
        WHERE lr.status = 'APPROVED'
        AND lr.employee.teamName = :team
        AND :date BETWEEN lr.startDate AND lr.endDate
    """)
    long countApprovedLeavesOnDate(
            @Param("team") String team,
            @Param("date") LocalDate date
    );
}
