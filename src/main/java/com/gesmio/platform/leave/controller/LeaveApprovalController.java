package com.gesmio.platform.leave.controller;

import com.gesmio.platform.common.crud.ReadOnlyCrudController;
import com.gesmio.platform.leave.entity.LeaveApproval;
import com.gesmio.platform.leave.service.LeaveApprovalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leave/leave-approvals")
public class LeaveApprovalController extends ReadOnlyCrudController<LeaveApproval> {

    public LeaveApprovalController(LeaveApprovalService service) {
        super(service);
    }
}
