package com.gesmio.platform.attendance.repository;

import com.gesmio.platform.attendance.entity.AttendanceRecord;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, UUID>, JpaSpecificationExecutor<AttendanceRecord> {

    Optional<AttendanceRecord> findByEmployeeIdAndAttendanceDate(UUID employeeId, LocalDate date);
    Page<AttendanceRecord> findByEmployeeIdAndAttendanceDateBetween(UUID employeeId, LocalDate from, LocalDate to, Pageable pageable);
}
