package com.gesmio.platform.identity.repository;

import com.gesmio.platform.identity.entity.PasswordResetToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID>, JpaSpecificationExecutor<PasswordResetToken> {
}
