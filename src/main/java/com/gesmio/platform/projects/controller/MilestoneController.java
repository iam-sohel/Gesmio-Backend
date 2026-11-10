package com.gesmio.platform.projects.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.projects.entity.Milestone;
import com.gesmio.platform.projects.service.MilestoneService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects/milestones")
public class MilestoneController extends CrudController<Milestone> {

    public MilestoneController(MilestoneService service) {
        super(service);
    }
}
