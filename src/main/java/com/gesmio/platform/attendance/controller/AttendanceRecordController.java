package com.gesmio.platform.attendance.controller;

import com.gesmio.platform.common.crud.ReadOnlyCrudController;
import com.gesmio.platform.attendance.entity.AttendanceRecord;
import com.gesmio.platform.attendance.service.AttendanceRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendance/attendance-records")
public class AttendanceRecordController extends ReadOnlyCrudController<AttendanceRecord> {

    public AttendanceRecordController(AttendanceRecordService service) {
        super(service);
    }
}
