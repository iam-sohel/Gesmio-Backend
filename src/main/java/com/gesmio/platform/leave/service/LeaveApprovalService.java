package com.gesmio.platform.leave.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.leave.entity.LeaveApproval;
import com.gesmio.platform.leave.repository.LeaveApprovalRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveApprovalService extends CrudService<LeaveApproval> {

    public LeaveApprovalService(LeaveApprovalRepository repository, AuditService auditService) {
        super(repository, repository, LeaveApproval.class, "leave_approvals", auditService);
    }
}
