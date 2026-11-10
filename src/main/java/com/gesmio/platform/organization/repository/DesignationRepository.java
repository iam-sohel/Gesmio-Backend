package com.gesmio.platform.organization.repository;

import com.gesmio.platform.organization.entity.Designation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DesignationRepository extends JpaRepository<Designation, UUID>, JpaSpecificationExecutor<Designation> {
}
