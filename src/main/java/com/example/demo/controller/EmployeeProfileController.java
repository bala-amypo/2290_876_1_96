package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeService;

    public EmployeeProfileController(EmployeeProfileService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeProfile> create(
            @RequestBody EmployeeProfile profile
    ) {
        return ResponseEntity.ok(employeeService.create(profile));
    }

    @GetMapping("/{team}")
public List<EmployeeProfile> getActiveEmployees(@PathVariable String team) {
    return employeeService.getActiveEmployeesByTeam(team);
}

}
