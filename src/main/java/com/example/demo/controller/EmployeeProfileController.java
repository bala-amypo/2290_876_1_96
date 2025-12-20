package com.example.demo.controller;

import com.example.demo.dto.EmployeeProfileDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    @PostMapping
    public String create(@RequestBody EmployeeProfileDto dto) {
        return "Employee created";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id) {
        return "Employee fetched";
    }
}
