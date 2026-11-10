package com.gesmio.platform.authorization.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.authorization.entity.RolePermission;
import com.gesmio.platform.authorization.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService extends CrudService<RolePermission> {

    public RolePermissionService(RolePermissionRepository repository, AuditService auditService) {
        super(repository, repository, RolePermission.class, "role_permissions", auditService);
    }
}
