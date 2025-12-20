package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

public interface CapacityAnalysisService {
    List<String> analyzeCapacity(String teamName, LocalDate start, LocalDate end);
}
