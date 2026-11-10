package com.gesmio.platform.leave.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.leave.entity.LeavePolicy;
import com.gesmio.platform.leave.service.LeavePolicyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leave/leave-policies")
public class LeavePolicyController extends CrudController<LeavePolicy> {

    public LeavePolicyController(LeavePolicyService service) {
        super(service);
    }
}
