package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.CapacityAlert;

public interface CapacityAlertRepository
        extends JpaRepository<CapacityAlert, Long> {
}
