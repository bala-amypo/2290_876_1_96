package com.example.demo.controller;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.model.LeaveRequest;
import com.example.demo.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@Tag(name = "Leave Request API")
public class LeaveRequestController {

    private final LeaveRequestService service;

    public LeaveRequestController(LeaveRequestService service) {
        this.service = service;
    }

    // POST /api/leaves
    @PostMapping
    public ResponseEntity<LeaveRequest> create(
            @RequestBody LeaveRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // PUT /api/leaves/{id}/approve
    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approve(@PathVariable Long id) {
        return ResponseEntity.ok(service.approve(id));
    }

    // PUT /api/leaves/{id}/reject
    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequest> reject(@PathVariable Long id) {
        return ResponseEntity.ok(service.reject(id));
    }

    // GET /api/leaves/employee/{id}
    @GetMapping("/employee/{id}")
    public ResponseEntity<List<LeaveRequest>> getByEmployee(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getByEmployee(id));
    }
}
