package com.example.demo.controller;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Profile API")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    
    @PostMapping
    public ResponseEntity<EmployeeProfile> createEmployee(
            @RequestBody EmployeeProfileDto dto) {

        return ResponseEntity.ok(service.create(dto));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeProfile> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeProfileDto dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProfile> getEmployee(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    
    @GetMapping("/team/{teamName}")
    public ResponseEntity<List<EmployeeProfile>> getByTeam(
            @PathVariable String teamName) {

        return ResponseEntity.ok(service.getByTeam(teamName));
    }
}
