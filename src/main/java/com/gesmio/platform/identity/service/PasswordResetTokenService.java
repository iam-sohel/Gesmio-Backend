package com.gesmio.platform.identity.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.identity.entity.PasswordResetToken;
import com.gesmio.platform.identity.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService extends CrudService<PasswordResetToken> {

    public PasswordResetTokenService(PasswordResetTokenRepository repository, AuditService auditService) {
        super(repository, repository, PasswordResetToken.class, "password_reset_tokens", auditService);
    }
}
