package com.gesmio.platform.projects.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.projects.entity.ProjectMember;
import com.gesmio.platform.projects.repository.ProjectMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberService extends CrudService<ProjectMember> {

    public ProjectMemberService(ProjectMemberRepository repository, AuditService auditService) {
        super(repository, repository, ProjectMember.class, "project_members", auditService);
    }
}
