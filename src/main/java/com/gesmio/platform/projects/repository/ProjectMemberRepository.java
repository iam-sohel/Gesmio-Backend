package com.gesmio.platform.projects.repository;

import com.gesmio.platform.projects.entity.ProjectMember;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID>, JpaSpecificationExecutor<ProjectMember> {

    List<ProjectMember> findByEmployeeIdAndLeftAtIsNull(UUID employeeId);
}
