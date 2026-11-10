package com.gesmio.platform.tasks.repository;

import com.gesmio.platform.tasks.entity.TaskAssignment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, UUID>, JpaSpecificationExecutor<TaskAssignment> {

    List<TaskAssignment> findByEmployeeIdAndRevokedAtIsNull(UUID employeeId);
    boolean existsByTaskIdAndEmployeeIdAndRevokedAtIsNull(UUID taskId, UUID employeeId);
}
