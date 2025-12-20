package com.example.demo.controller;

import com.example.demo.dto.LeaveRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    @PostMapping
    public String submit(@RequestBody LeaveRequestDto dto) {
        return "Leave submitted";
    }

    @PutMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        return "Leave approved";
    }

    @PutMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        return "Leave rejected";
    }
}
