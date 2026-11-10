package com.gesmio.platform.projects.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.projects.entity.Project;
import com.gesmio.platform.projects.service.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects/projects")
public class ProjectController extends CrudController<Project> {

    public ProjectController(ProjectService service) {
        super(service);
    }
}
