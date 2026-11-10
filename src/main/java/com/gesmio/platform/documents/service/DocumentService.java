package com.gesmio.platform.documents.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.documents.entity.Document;
import com.gesmio.platform.documents.repository.DocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentService extends CrudService<Document> {

    public DocumentService(DocumentRepository repository, AuditService auditService) {
        super(repository, repository, Document.class, "documents", auditService);
    }
}
