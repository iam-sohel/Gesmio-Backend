package com.gesmio.platform.leave.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.leave.entity.LeaveBalance;
import com.gesmio.platform.leave.repository.LeaveBalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveBalanceService extends CrudService<LeaveBalance> {

    public LeaveBalanceService(LeaveBalanceRepository repository, AuditService auditService) {
        super(repository, repository, LeaveBalance.class, "leave_balances", auditService);
    }
}
