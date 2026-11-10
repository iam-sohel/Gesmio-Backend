package com.gesmio.platform.leave.controller;

import com.gesmio.platform.common.crud.PageResponse;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.employees.service.CurrentEmployeeService;
import com.gesmio.platform.leave.dto.LeaveDtos.ApplyLeaveRequest;
import com.gesmio.platform.leave.entity.LeaveBalance;
import com.gesmio.platform.leave.entity.LeaveRequest;
import com.gesmio.platform.leave.repository.LeaveRequestRepository;
import com.gesmio.platform.leave.service.LeaveWorkflowService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Employee self-service for leave. */
@RestController
@RequestMapping("/api/v1/me")
public class MeLeaveController {

    private final LeaveWorkflowService workflow;
    private final LeaveRequestRepository requests;
    private final CurrentEmployeeService currentEmployee;

    public MeLeaveController(LeaveWorkflowService workflow, LeaveRequestRepository requests,
                             CurrentEmployeeService currentEmployee) {
        this.workflow = workflow;
        this.requests = requests;
        this.currentEmployee = currentEmployee;
    }

    @GetMapping("/leave-requests")
    public PageResponse<LeaveRequest> mine(
            @PageableDefault(size = 20, sort = "submittedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return PageResponse.of(requests.findByEmployeeId(currentEmployee.require().getId(), pageable));
    }

    @PostMapping("/leave-requests")
    @ResponseStatus(HttpStatus.CREATED)
    public LeaveRequest apply(@Valid @RequestBody ApplyLeaveRequest body) {
        Employee employee = currentEmployee.require();
        return workflow.apply(employee, body);
    }

    @PostMapping("/leave-requests/{id}/cancel")
    public LeaveRequest cancel(@PathVariable UUID id) {
        return workflow.cancel(currentEmployee.require(), id);
    }

    @GetMapping("/leave-balances")
    public List<LeaveBalance> balances(@RequestParam(required = false) Integer year) {
        int y = year != null ? year : LocalDate.now().getYear();
        return workflow.balances(currentEmployee.require().getId(), y);
    }
}
