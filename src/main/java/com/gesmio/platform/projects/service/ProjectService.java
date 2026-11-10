package com.gesmio.platform.projects.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.projects.entity.Project;
import com.gesmio.platform.projects.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends CrudService<Project> {

    public ProjectService(ProjectRepository repository, AuditService auditService) {
        super(repository, repository, Project.class, "projects", auditService);
    }
}
