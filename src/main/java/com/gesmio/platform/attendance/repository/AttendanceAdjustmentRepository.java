package com.gesmio.platform.attendance.repository;

import com.gesmio.platform.attendance.entity.AttendanceAdjustment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttendanceAdjustmentRepository extends JpaRepository<AttendanceAdjustment, UUID>, JpaSpecificationExecutor<AttendanceAdjustment> {
}
