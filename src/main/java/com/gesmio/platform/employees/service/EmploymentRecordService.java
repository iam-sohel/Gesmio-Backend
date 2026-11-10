package com.gesmio.platform.employees.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.employees.entity.EmploymentRecord;
import com.gesmio.platform.employees.repository.EmploymentRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class EmploymentRecordService extends CrudService<EmploymentRecord> {

    public EmploymentRecordService(EmploymentRecordRepository repository, AuditService auditService) {
        super(repository, repository, EmploymentRecord.class, "employment_records", auditService);
    }
}
