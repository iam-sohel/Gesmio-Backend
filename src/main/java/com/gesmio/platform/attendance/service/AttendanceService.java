package com.gesmio.platform.attendance.service;

import com.gesmio.platform.attendance.dto.AttendanceDtos.AdjustmentDecision;
import com.gesmio.platform.attendance.dto.AttendanceDtos.AdjustmentRequest;
import com.gesmio.platform.attendance.entity.AttendanceAdjustment;
import com.gesmio.platform.attendance.entity.AttendanceRecord;
import com.gesmio.platform.attendance.repository.AttendanceAdjustmentRepository;
import com.gesmio.platform.attendance.repository.AttendanceRecordRepository;
import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.PageResponse;
import com.gesmio.platform.common.exception.BusinessException;
import com.gesmio.platform.common.exception.ConflictException;
import com.gesmio.platform.common.exception.ResourceNotFoundException;
import com.gesmio.platform.common.security.SecurityUtils;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.notifications.service.NotificationPublisher;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceRecordRepository records;
    private final AttendanceAdjustmentRepository adjustments;
    private final NotificationPublisher notifications;
    private final AuditService audit;
    private final ZoneId zone;

    public AttendanceService(AttendanceRecordRepository records, AttendanceAdjustmentRepository adjustments,
                             NotificationPublisher notifications, AuditService audit,
                             @Value("${app.timezone}") String timezone) {
        this.records = records;
        this.adjustments = adjustments;
        this.notifications = notifications;
        this.audit = audit;
        this.zone = ZoneId.of(timezone);
    }

    public AttendanceRecord checkIn(Employee employee, String workMode) {
        LocalDate today = LocalDate.now(zone);
        if (records.findByEmployeeIdAndAttendanceDate(employee.getId(), today).isPresent()) {
            throw new ConflictException("Already checked in today");
        }
        AttendanceRecord r = new AttendanceRecord();
        r.setEmployeeId(employee.getId());
        r.setAttendanceDate(today);
        r.setWorkMode(workMode != null && !workMode.isBlank() ? workMode.toUpperCase() : "OFFICE");
        r.setCheckInAt(Instant.now());
        r.setStatus("PRESENT");
        return records.save(r);
    }

    public AttendanceRecord checkOut(Employee employee) {
        AttendanceRecord r = records.findByEmployeeIdAndAttendanceDate(employee.getId(), LocalDate.now(zone))
                .orElseThrow(() -> new BusinessException("You have not checked in today"));
        if (r.getCheckOutAt() != null) {
            throw new ConflictException("Already checked out today");
        }
        r.setCheckOutAt(Instant.now());
        r.setTotalWorkMinutes(minutes(r.getCheckInAt(), r.getCheckOutAt()));
        return r;
    }

    @Transactional(readOnly = true)
    public PageResponse<AttendanceRecord> history(Employee employee, LocalDate from, LocalDate to, Pageable pageable) {
        return PageResponse.of(records.findByEmployeeIdAndAttendanceDateBetween(employee.getId(), from, to, pageable));
    }

    public AttendanceAdjustment requestAdjustment(Employee employee, AdjustmentRequest dto) {
        AttendanceRecord record = records.findById(dto.attendanceRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
        if (!record.getEmployeeId().equals(employee.getId())) {
            throw new ResourceNotFoundException("Attendance record not found");
        }
        if (dto.newCheckInAt() == null && dto.newCheckOutAt() == null) {
            throw new BusinessException("Provide newCheckInAt and/or newCheckOutAt");
        }
        AttendanceAdjustment a = new AttendanceAdjustment();
        a.setAttendanceRecordId(record.getId());
        a.setRequestedBy(SecurityUtils.currentUserId());
        a.setReason(dto.reason());
        a.setOldCheckInAt(record.getCheckInAt());
        a.setOldCheckOutAt(record.getCheckOutAt());
        a.setNewCheckInAt(dto.newCheckInAt());
        a.setNewCheckOutAt(dto.newCheckOutAt());
        a.setStatus("PENDING");
        a = adjustments.save(a);
        audit.record("ATTENDANCE_ADJUST_REQUEST", "attendance_adjustments", a.getId(), "SUCCESS");
        return a;
    }

    public AttendanceAdjustment review(UUID adjustmentId, AdjustmentDecision dto) {
        String decision = dto.decision().trim().toUpperCase();
        if (!decision.equals("APPROVED") && !decision.equals("REJECTED")) {
            throw new BusinessException("decision must be APPROVED or REJECTED");
        }
        AttendanceAdjustment a = adjustments.findById(adjustmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Adjustment not found"));
        if (!"PENDING".equals(a.getStatus())) {
            throw new BusinessException("Adjustment already " + a.getStatus());
        }
        UUID reviewer = SecurityUtils.currentUserId();
        if (reviewer.equals(a.getRequestedBy()) && !SecurityUtils.hasRole("ADMIN")) {
            throw new BusinessException("You cannot review your own adjustment");
        }
        a.setStatus(decision);
        a.setReviewedBy(reviewer);
        if (decision.equals("APPROVED")) {
            AttendanceRecord r = records.findById(a.getAttendanceRecordId()).orElseThrow();
            if (a.getNewCheckInAt() != null) {
                r.setCheckInAt(a.getNewCheckInAt());
            }
            if (a.getNewCheckOutAt() != null) {
                r.setCheckOutAt(a.getNewCheckOutAt());
            }
            if (r.getCheckInAt() != null && r.getCheckOutAt() != null) {
                if (r.getCheckOutAt().isBefore(r.getCheckInAt())) {
                    throw new BusinessException("Adjusted check-out is before check-in");
                }
                r.setTotalWorkMinutes(minutes(r.getCheckInAt(), r.getCheckOutAt()));
            }
        }
        notifications.notify(a.getRequestedBy(), "ATTENDANCE_ADJUSTMENT",
                "Attendance adjustment " + decision.toLowerCase(), a.getReason(), "attendance_adjustments", a.getId());
        audit.record("ATTENDANCE_ADJUST_" + decision, "attendance_adjustments", a.getId(), "SUCCESS");
        return a;
    }

    private static int minutes(Instant from, Instant to) {
        return (int) Duration.between(from, to).toMinutes();
    }
}
