package com.gesmio.platform.leave.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.leave.entity.LeaveBalanceTransaction;
import com.gesmio.platform.leave.repository.LeaveBalanceTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveBalanceTransactionService extends CrudService<LeaveBalanceTransaction> {

    public LeaveBalanceTransactionService(LeaveBalanceTransactionRepository repository, AuditService auditService) {
        super(repository, repository, LeaveBalanceTransaction.class, "leave_balance_transactions", auditService);
    }
}
