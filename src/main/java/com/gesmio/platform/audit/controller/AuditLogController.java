package com.gesmio.platform.audit.controller;

import com.gesmio.platform.common.crud.ReadOnlyCrudController;
import com.gesmio.platform.audit.entity.AuditLog;
import com.gesmio.platform.audit.service.AuditLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit/audit-logs")
public class AuditLogController extends ReadOnlyCrudController<AuditLog> {

    public AuditLogController(AuditLogService service) {
        super(service);
    }
}
