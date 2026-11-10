package com.gesmio.platform.identity.repository;

import com.gesmio.platform.identity.entity.MfaCredential;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MfaCredentialRepository extends JpaRepository<MfaCredential, UUID>, JpaSpecificationExecutor<MfaCredential> {
}
