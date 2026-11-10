package com.gesmio.platform.authorization.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.authorization.entity.UserRole;
import com.gesmio.platform.authorization.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService extends CrudService<UserRole> {

    public UserRoleService(UserRoleRepository repository, AuditService auditService) {
        super(repository, repository, UserRole.class, "user_roles", auditService);
    }
}
