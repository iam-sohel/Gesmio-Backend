package com.gesmio.platform.projects.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.projects.entity.ProjectMember;
import com.gesmio.platform.projects.service.ProjectMemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects/project-members")
public class ProjectMemberController extends CrudController<ProjectMember> {

    public ProjectMemberController(ProjectMemberService service) {
        super(service);
    }
}
