package com.gesmio.platform.recruitment.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.recruitment.entity.JobOffer;
import com.gesmio.platform.recruitment.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

@Service
public class JobOfferService extends CrudService<JobOffer> {

    public JobOfferService(JobOfferRepository repository, AuditService auditService) {
        super(repository, repository, JobOffer.class, "job_offers", auditService);
    }
}
