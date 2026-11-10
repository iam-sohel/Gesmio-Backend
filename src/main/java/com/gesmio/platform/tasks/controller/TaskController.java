package com.gesmio.platform.tasks.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.tasks.entity.Task;
import com.gesmio.platform.tasks.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks/tasks")
public class TaskController extends CrudController<Task> {

    public TaskController(TaskService service) {
        super(service);
    }
}
