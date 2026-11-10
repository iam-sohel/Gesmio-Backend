package com.gesmio.platform.documents.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.documents.entity.DocumentPermission;
import com.gesmio.platform.documents.repository.DocumentPermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentPermissionService extends CrudService<DocumentPermission> {

    public DocumentPermissionService(DocumentPermissionRepository repository, AuditService auditService) {
        super(repository, repository, DocumentPermission.class, "document_permissions", auditService);
    }
}
