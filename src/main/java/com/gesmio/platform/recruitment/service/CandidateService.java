package com.gesmio.platform.recruitment.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.recruitment.entity.Candidate;
import com.gesmio.platform.recruitment.repository.CandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService extends CrudService<Candidate> {

    public CandidateService(CandidateRepository repository, AuditService auditService) {
        super(repository, repository, Candidate.class, "candidates", auditService);
    }
}
