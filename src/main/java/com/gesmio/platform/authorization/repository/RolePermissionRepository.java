package com.gesmio.platform.authorization.repository;

import com.gesmio.platform.authorization.entity.RolePermission;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID>, JpaSpecificationExecutor<RolePermission> {

    List<RolePermission> findByRoleIdIn(Collection<UUID> roleIds);
    boolean existsByRoleIdAndPermissionId(UUID roleId, UUID permissionId);
}
