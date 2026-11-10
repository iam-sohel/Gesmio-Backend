package com.gesmio.platform.organization.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.organization.entity.Department;
import com.gesmio.platform.organization.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends CrudService<Department> {

    public DepartmentService(DepartmentRepository repository, AuditService auditService) {
        super(repository, repository, Department.class, "departments", auditService);
    }
}
