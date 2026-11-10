package com.gesmio.platform.recruitment.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.recruitment.entity.JobApplication;
import com.gesmio.platform.recruitment.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationService extends CrudService<JobApplication> {

    public JobApplicationService(JobApplicationRepository repository, AuditService auditService) {
        super(repository, repository, JobApplication.class, "job_applications", auditService);
    }
}
