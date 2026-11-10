package com.gesmio.platform.employees.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.employees.entity.ReportingRelationship;
import com.gesmio.platform.employees.repository.ReportingRelationshipRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportingRelationshipService extends CrudService<ReportingRelationship> {

    public ReportingRelationshipService(ReportingRelationshipRepository repository, AuditService auditService) {
        super(repository, repository, ReportingRelationship.class, "reporting_relationships", auditService);
    }
}
