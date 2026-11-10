package com.gesmio.platform.leave.repository;

import com.gesmio.platform.leave.entity.LeaveBalanceTransaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeaveBalanceTransactionRepository extends JpaRepository<LeaveBalanceTransaction, UUID>, JpaSpecificationExecutor<LeaveBalanceTransaction> {
}
