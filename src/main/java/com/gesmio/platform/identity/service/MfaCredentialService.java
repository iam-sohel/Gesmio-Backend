package com.gesmio.platform.identity.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.identity.entity.MfaCredential;
import com.gesmio.platform.identity.repository.MfaCredentialRepository;
import org.springframework.stereotype.Service;

@Service
public class MfaCredentialService extends CrudService<MfaCredential> {

    public MfaCredentialService(MfaCredentialRepository repository, AuditService auditService) {
        super(repository, repository, MfaCredential.class, "mfa_credentials", auditService);
    }
}
