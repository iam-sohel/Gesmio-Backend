package com.gesmio.platform.audit.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.audit.entity.AuditLog;
import com.gesmio.platform.audit.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService extends CrudService<AuditLog> {

    public AuditLogService(AuditLogRepository repository, AuditService auditService) {
        super(repository, repository, AuditLog.class, "audit_logs", auditService);
    }
}
