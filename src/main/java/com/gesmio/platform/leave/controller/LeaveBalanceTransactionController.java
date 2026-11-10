package com.gesmio.platform.leave.controller;

import com.gesmio.platform.common.crud.ReadOnlyCrudController;
import com.gesmio.platform.leave.entity.LeaveBalanceTransaction;
import com.gesmio.platform.leave.service.LeaveBalanceTransactionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leave/leave-balance-transactions")
public class LeaveBalanceTransactionController extends ReadOnlyCrudController<LeaveBalanceTransaction> {

    public LeaveBalanceTransactionController(LeaveBalanceTransactionService service) {
        super(service);
    }
}
