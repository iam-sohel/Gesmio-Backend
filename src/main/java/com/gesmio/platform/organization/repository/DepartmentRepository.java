package com.gesmio.platform.organization.repository;

import com.gesmio.platform.organization.entity.Department;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentRepository extends JpaRepository<Department, UUID>, JpaSpecificationExecutor<Department> {
}
