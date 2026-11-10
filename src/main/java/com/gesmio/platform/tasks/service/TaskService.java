package com.gesmio.platform.tasks.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.tasks.entity.Task;
import com.gesmio.platform.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends CrudService<Task> {

    public TaskService(TaskRepository repository, AuditService auditService) {
        super(repository, repository, Task.class, "tasks", auditService);
    }
}
