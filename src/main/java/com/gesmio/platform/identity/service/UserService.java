package com.gesmio.platform.identity.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.identity.entity.User;
import com.gesmio.platform.identity.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudService<User> {

    public UserService(UserRepository repository, AuditService auditService) {
        super(repository, repository, User.class, "users", auditService);
    }
}
