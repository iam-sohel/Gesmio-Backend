package com.gesmio.platform.leave.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.leave.entity.LeaveBalance;
import com.gesmio.platform.leave.service.LeaveBalanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leave/leave-balances")
public class LeaveBalanceController extends CrudController<LeaveBalance> {

    public LeaveBalanceController(LeaveBalanceService service) {
        super(service);
    }
}
