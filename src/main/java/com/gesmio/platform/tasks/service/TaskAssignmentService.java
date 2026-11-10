package com.gesmio.platform.tasks.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.tasks.entity.TaskAssignment;
import com.gesmio.platform.tasks.repository.TaskAssignmentRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskAssignmentService extends CrudService<TaskAssignment> {

    public TaskAssignmentService(TaskAssignmentRepository repository, AuditService auditService) {
        super(repository, repository, TaskAssignment.class, "task_assignments", auditService);
    }
}
