package com.gesmio.platform.attendance.controller;

import com.gesmio.platform.common.crud.ReadOnlyCrudController;
import com.gesmio.platform.attendance.entity.AttendanceAdjustment;
import com.gesmio.platform.attendance.service.AttendanceAdjustmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendance/attendance-adjustments")
public class AttendanceAdjustmentController extends ReadOnlyCrudController<AttendanceAdjustment> {

    public AttendanceAdjustmentController(AttendanceAdjustmentService service) {
        super(service);
    }
}
