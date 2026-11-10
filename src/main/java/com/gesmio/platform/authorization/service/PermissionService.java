package com.gesmio.platform.authorization.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.authorization.entity.Permission;
import com.gesmio.platform.authorization.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends CrudService<Permission> {

    public PermissionService(PermissionRepository repository, AuditService auditService) {
        super(repository, repository, Permission.class, "permissions", auditService);
    }
}
