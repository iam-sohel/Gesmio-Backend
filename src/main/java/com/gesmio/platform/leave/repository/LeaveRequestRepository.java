package com.gesmio.platform.leave.repository;

import com.gesmio.platform.leave.entity.LeaveRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, UUID>, JpaSpecificationExecutor<LeaveRequest> {

    Page<LeaveRequest> findByEmployeeId(UUID employeeId, Pageable pageable);
    Page<LeaveRequest> findByStatus(String status, Pageable pageable);
    boolean existsByEmployeeIdAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(UUID employeeId, Collection<String> statuses, LocalDate end, LocalDate start);
}
