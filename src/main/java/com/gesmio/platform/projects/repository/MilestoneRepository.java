package com.gesmio.platform.projects.repository;

import com.gesmio.platform.projects.entity.Milestone;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MilestoneRepository extends JpaRepository<Milestone, UUID>, JpaSpecificationExecutor<Milestone> {
}
