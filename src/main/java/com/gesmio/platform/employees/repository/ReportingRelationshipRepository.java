package com.gesmio.platform.employees.repository;

import com.gesmio.platform.employees.entity.ReportingRelationship;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReportingRelationshipRepository extends JpaRepository<ReportingRelationship, UUID>, JpaSpecificationExecutor<ReportingRelationship> {

    Optional<ReportingRelationship> findFirstByEmployeeIdAndEffectiveToIsNull(UUID employeeId);
}
