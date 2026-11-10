package com.gesmio.platform.recruitment.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.recruitment.entity.Interview;
import com.gesmio.platform.recruitment.repository.InterviewRepository;
import org.springframework.stereotype.Service;

@Service
public class InterviewService extends CrudService<Interview> {

    public InterviewService(InterviewRepository repository, AuditService auditService) {
        super(repository, repository, Interview.class, "interviews", auditService);
    }
}
