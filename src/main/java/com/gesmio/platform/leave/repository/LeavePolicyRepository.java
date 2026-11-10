package com.gesmio.platform.leave.repository;

import com.gesmio.platform.leave.entity.LeavePolicy;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeavePolicyRepository extends JpaRepository<LeavePolicy, UUID>, JpaSpecificationExecutor<LeavePolicy> {
}
