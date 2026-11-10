package com.gesmio.platform.authorization.repository;

import com.gesmio.platform.authorization.entity.UserRole;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID>, JpaSpecificationExecutor<UserRole> {

    List<UserRole> findByUserIdAndRevokedAtIsNull(UUID userId);
}
