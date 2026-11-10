package com.gesmio.platform.attendance.controller;

import com.gesmio.platform.attendance.dto.AttendanceDtos.AdjustmentDecision;
import com.gesmio.platform.attendance.entity.AttendanceAdjustment;
import com.gesmio.platform.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager/attendance-adjustments")
public class ManagerAttendanceController {

    private final AttendanceService service;

    public ManagerAttendanceController(AttendanceService service) {
        this.service = service;
    }

    @PostMapping("/{id}/decision")
    public AttendanceAdjustment decide(@PathVariable UUID id, @Valid @RequestBody AdjustmentDecision body) {
        return service.review(id, body);
    }
}
