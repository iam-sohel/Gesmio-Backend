package com.gesmio.platform.organization.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.organization.entity.Designation;
import com.gesmio.platform.organization.repository.DesignationRepository;
import org.springframework.stereotype.Service;

@Service
public class DesignationService extends CrudService<Designation> {

    public DesignationService(DesignationRepository repository, AuditService auditService) {
        super(repository, repository, Designation.class, "designations", auditService);
    }
}
