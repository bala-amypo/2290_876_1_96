package com.example.demo.service;

import com.example.demo.model.CapacityAlert;
import java.util.List;

public interface CapacityAnalysisService {

    List<CapacityAlert> generateAlerts();
}
