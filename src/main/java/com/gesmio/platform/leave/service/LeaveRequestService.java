package com.gesmio.platform.leave.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.leave.entity.LeaveRequest;
import com.gesmio.platform.leave.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestService extends CrudService<LeaveRequest> {

    public LeaveRequestService(LeaveRequestRepository repository, AuditService auditService) {
        super(repository, repository, LeaveRequest.class, "leave_requests", auditService);
    }
}
