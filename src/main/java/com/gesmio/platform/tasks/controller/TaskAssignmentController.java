package com.gesmio.platform.tasks.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.tasks.entity.TaskAssignment;
import com.gesmio.platform.tasks.service.TaskAssignmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks/task-assignments")
public class TaskAssignmentController extends CrudController<TaskAssignment> {

    public TaskAssignmentController(TaskAssignmentService service) {
        super(service);
    }
}
