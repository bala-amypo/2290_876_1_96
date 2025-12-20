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

    @PostMapping
    public ResponseEntity<LeaveRequestDto> create(@RequestBody LeaveRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approve(@PathVariable Long id) {
        return ResponseEntity.ok(service.approve(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequest> reject(@PathVariable Long id) {
        return ResponseEntity.ok(service.reject(id));
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<LeaveRequest>> getByEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByEmployee(id));
    }
}
