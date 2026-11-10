package com.gesmio.platform.recruitment.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.recruitment.entity.CandidateDocument;
import com.gesmio.platform.recruitment.repository.CandidateDocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateDocumentService extends CrudService<CandidateDocument> {

    public CandidateDocumentService(CandidateDocumentRepository repository, AuditService auditService) {
        super(repository, repository, CandidateDocument.class, "candidate_documents", auditService);
    }
}
