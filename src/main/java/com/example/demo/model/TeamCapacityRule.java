package com.example.demo.model;

/**
 * API Alias for TeamCapacityConfig.
 * Required because controllers/services reference TeamCapacityRule
 * but persistence uses TeamCapacityConfig.
 */
public class TeamCapacityRule extends TeamCapacityConfig {
    // NO extra fields
    // NO extra logic
}
