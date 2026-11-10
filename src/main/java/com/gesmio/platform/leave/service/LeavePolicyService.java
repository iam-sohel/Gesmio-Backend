package com.gesmio.platform.leave.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.leave.entity.LeavePolicy;
import com.gesmio.platform.leave.repository.LeavePolicyRepository;
import org.springframework.stereotype.Service;

@Service
public class LeavePolicyService extends CrudService<LeavePolicy> {

    public LeavePolicyService(LeavePolicyRepository repository, AuditService auditService) {
        super(repository, repository, LeavePolicy.class, "leave_policies", auditService);
    }
}
