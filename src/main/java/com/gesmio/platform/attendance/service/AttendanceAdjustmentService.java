package com.gesmio.platform.attendance.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.attendance.entity.AttendanceAdjustment;
import com.gesmio.platform.attendance.repository.AttendanceAdjustmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceAdjustmentService extends CrudService<AttendanceAdjustment> {

    public AttendanceAdjustmentService(AttendanceAdjustmentRepository repository, AuditService auditService) {
        super(repository, repository, AttendanceAdjustment.class, "attendance_adjustments", auditService);
    }
}
