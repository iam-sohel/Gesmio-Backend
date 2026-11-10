package com.gesmio.platform.projects.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.projects.entity.Milestone;
import com.gesmio.platform.projects.repository.MilestoneRepository;
import org.springframework.stereotype.Service;

@Service
public class MilestoneService extends CrudService<Milestone> {

    public MilestoneService(MilestoneRepository repository, AuditService auditService) {
        super(repository, repository, Milestone.class, "milestones", auditService);
    }
}
