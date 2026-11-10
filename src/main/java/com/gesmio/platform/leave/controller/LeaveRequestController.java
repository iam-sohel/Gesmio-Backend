package com.gesmio.platform.leave.controller;

import com.gesmio.platform.common.crud.ReadOnlyCrudController;
import com.gesmio.platform.leave.entity.LeaveRequest;
import com.gesmio.platform.leave.service.LeaveRequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leave/leave-requests")
public class LeaveRequestController extends ReadOnlyCrudController<LeaveRequest> {

    public LeaveRequestController(LeaveRequestService service) {
        super(service);
    }
}
