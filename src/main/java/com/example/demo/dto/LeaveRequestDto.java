package com.example.demo.dto;

import java.time.LocalDate;

public class LeaveRequestDto {
    public String employeeId;
    public LocalDate startDate;
    public LocalDate endDate;
    public String type;
    public String reason;
}
