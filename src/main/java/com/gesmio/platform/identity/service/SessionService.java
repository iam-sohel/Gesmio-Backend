package com.gesmio.platform.identity.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.identity.entity.Session;
import com.gesmio.platform.identity.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService extends CrudService<Session> {

    public SessionService(SessionRepository repository, AuditService auditService) {
        super(repository, repository, Session.class, "sessions", auditService);
    }
}
