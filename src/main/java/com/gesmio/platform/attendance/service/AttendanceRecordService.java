package com.gesmio.platform.attendance.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.attendance.entity.AttendanceRecord;
import com.gesmio.platform.attendance.repository.AttendanceRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceRecordService extends CrudService<AttendanceRecord> {

    public AttendanceRecordService(AttendanceRecordRepository repository, AuditService auditService) {
        super(repository, repository, AttendanceRecord.class, "attendance_records", auditService);
    }
}
