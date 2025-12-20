package com.example.demo.service.impl;

import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    @Override
    public List<String> analyzeCapacity(String teamName, LocalDate start, LocalDate end) {
        return new ArrayList<>(); // dummy logic for now
    }
}
