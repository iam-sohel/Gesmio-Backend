package com.gesmio.platform.attendance.controller;

import com.gesmio.platform.attendance.dto.AttendanceDtos.AdjustmentRequest;
import com.gesmio.platform.attendance.dto.AttendanceDtos.CheckInRequest;
import com.gesmio.platform.attendance.entity.AttendanceAdjustment;
import com.gesmio.platform.attendance.entity.AttendanceRecord;
import com.gesmio.platform.attendance.service.AttendanceService;
import com.gesmio.platform.common.crud.PageResponse;
import com.gesmio.platform.employees.service.CurrentEmployeeService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me/attendance")
public class MeAttendanceController {

    private final AttendanceService service;
    private final CurrentEmployeeService currentEmployee;

    public MeAttendanceController(AttendanceService service, CurrentEmployeeService currentEmployee) {
        this.service = service;
        this.currentEmployee = currentEmployee;
    }

    @PostMapping("/check-in")
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceRecord checkIn(@RequestBody(required = false) CheckInRequest body) {
        return service.checkIn(currentEmployee.require(), body != null ? body.workMode() : null);
    }

    @PostMapping("/check-out")
    public AttendanceRecord checkOut() {
        return service.checkOut(currentEmployee.require());
    }

    /** GET ?from=2026-11-01&to=2026-11-30 (defaults to the current month) */
    @GetMapping
    public PageResponse<AttendanceRecord> history(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(size = 31, sort = "attendanceDate", direction = Sort.Direction.DESC) Pageable pageable) {
        LocalDate today = LocalDate.now();
        LocalDate start = from != null ? from : today.withDayOfMonth(1);
        LocalDate end = to != null ? to : today.withDayOfMonth(today.lengthOfMonth());
        return service.history(currentEmployee.require(), start, end, pageable);
    }

    @PostMapping("/adjustments")
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceAdjustment requestAdjustment(@Valid @RequestBody AdjustmentRequest body) {
        return service.requestAdjustment(currentEmployee.require(), body);
    }
}
