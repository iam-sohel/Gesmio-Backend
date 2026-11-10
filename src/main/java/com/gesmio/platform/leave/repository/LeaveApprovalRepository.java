package com.gesmio.platform.leave.repository;

import com.gesmio.platform.leave.entity.LeaveApproval;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeaveApprovalRepository extends JpaRepository<LeaveApproval, UUID>, JpaSpecificationExecutor<LeaveApproval> {
}
