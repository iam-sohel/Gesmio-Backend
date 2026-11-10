package com.gesmio.platform.recruitment.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.recruitment.entity.JobOpening;
import com.gesmio.platform.recruitment.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;

@Service
public class JobOpeningService extends CrudService<JobOpening> {

    public JobOpeningService(JobOpeningRepository repository, AuditService auditService) {
        super(repository, repository, JobOpening.class, "job_openings", auditService);
    }
}
