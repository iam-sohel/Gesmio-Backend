package com.gesmio.platform.documents.repository;

import com.gesmio.platform.documents.entity.DocumentPermission;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentPermissionRepository extends JpaRepository<DocumentPermission, UUID>, JpaSpecificationExecutor<DocumentPermission> {
}
