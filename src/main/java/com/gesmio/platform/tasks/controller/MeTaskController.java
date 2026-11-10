package com.gesmio.platform.tasks.controller;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.exception.BusinessException;
import com.gesmio.platform.common.exception.ResourceNotFoundException;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.employees.service.CurrentEmployeeService;
import com.gesmio.platform.projects.entity.Project;
import com.gesmio.platform.projects.repository.ProjectMemberRepository;
import com.gesmio.platform.projects.repository.ProjectRepository;
import com.gesmio.platform.tasks.entity.Task;
import com.gesmio.platform.tasks.entity.TaskAssignment;
import com.gesmio.platform.tasks.repository.TaskAssignmentRepository;
import com.gesmio.platform.tasks.repository.TaskRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** "My work": tasks assigned to me, projects I belong to, and status updates on my tasks. */
@RestController
@RequestMapping("/api/v1/me")
@Transactional
public class MeTaskController {

    public record StatusRequest(@NotBlank String status) {
    }

    private static final Set<String> STATUSES = Set.of("TODO", "IN_PROGRESS", "BLOCKED", "IN_REVIEW", "DONE");

    private final CurrentEmployeeService currentEmployee;
    private final TaskAssignmentRepository assignments;
    private final TaskRepository tasks;
    private final ProjectMemberRepository members;
    private final ProjectRepository projects;
    private final AuditService audit;

    public MeTaskController(CurrentEmployeeService currentEmployee, TaskAssignmentRepository assignments,
                            TaskRepository tasks, ProjectMemberRepository members, ProjectRepository projects,
                            AuditService audit) {
        this.currentEmployee = currentEmployee;
        this.assignments = assignments;
        this.tasks = tasks;
        this.members = members;
        this.projects = projects;
        this.audit = audit;
    }

    @GetMapping("/tasks")
    @Transactional(readOnly = true)
    public List<Task> myTasks() {
        Employee e = currentEmployee.require();
        List<UUID> taskIds = assignments.findByEmployeeIdAndRevokedAtIsNull(e.getId()).stream()
                .map(TaskAssignment::getTaskId).distinct().toList();
        return tasks.findAllById(taskIds);
    }

    @GetMapping("/projects")
    @Transactional(readOnly = true)
    public List<Project> myProjects() {
        Employee e = currentEmployee.require();
        List<UUID> projectIds = members.findByEmployeeIdAndLeftAtIsNull(e.getId()).stream()
                .map(m -> m.getProjectId()).distinct().toList();
        return projects.findAllById(projectIds);
    }

    @PostMapping("/tasks/{id}/status")
    public Task updateStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        String status = body.status().trim().toUpperCase();
        if (!STATUSES.contains(status)) {
            throw new BusinessException("status must be one of " + STATUSES);
        }
        Employee e = currentEmployee.require();
        if (!assignments.existsByTaskIdAndEmployeeIdAndRevokedAtIsNull(id, e.getId())) {
            throw new ResourceNotFoundException("Task not found or not assigned to you");
        }
        Task task = tasks.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setStatus(status);
        audit.record("TASK_STATUS", "tasks", id, "SUCCESS");
        return task;
    }
}
