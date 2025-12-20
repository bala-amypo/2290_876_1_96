package com.example.demo.controller;

import com.example.demo.model.LeaveRequest;
import com.example.demo.service.LeaveRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    private final LeaveRequestService leaveService;

    public LeaveRequestController(LeaveRequestService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public ResponseEntity<LeaveRequest> applyLeave(
            @RequestBody LeaveRequest request
    ) {
        return ResponseEntity.ok(leaveService.applyLeave(request));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approve(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(leaveService.approveLeave(id));
    }
}
