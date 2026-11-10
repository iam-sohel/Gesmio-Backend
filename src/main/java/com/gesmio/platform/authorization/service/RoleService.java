package com.gesmio.platform.authorization.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.authorization.entity.Role;
import com.gesmio.platform.authorization.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends CrudService<Role> {

    public RoleService(RoleRepository repository, AuditService auditService) {
        super(repository, repository, Role.class, "roles", auditService);
    }
}
