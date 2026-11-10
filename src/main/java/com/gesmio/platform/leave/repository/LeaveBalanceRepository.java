package com.gesmio.platform.leave.repository;

import com.gesmio.platform.leave.entity.LeaveBalance;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, UUID>, JpaSpecificationExecutor<LeaveBalance> {

    Optional<LeaveBalance> findByEmployeeIdAndLeavePolicyIdAndLeaveYear(UUID employeeId, UUID leavePolicyId, Integer leaveYear);
    List<LeaveBalance> findByEmployeeIdAndLeaveYear(UUID employeeId, Integer leaveYear);
}
