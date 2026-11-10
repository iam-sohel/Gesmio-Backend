package com.gesmio.platform.employees.repository;

import com.gesmio.platform.employees.entity.EmploymentRecord;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmploymentRecordRepository extends JpaRepository<EmploymentRecord, UUID>, JpaSpecificationExecutor<EmploymentRecord> {
}
