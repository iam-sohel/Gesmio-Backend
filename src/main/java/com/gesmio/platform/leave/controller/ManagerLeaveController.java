package com.gesmio.platform.leave.controller;

import com.gesmio.platform.common.crud.PageResponse;
import com.gesmio.platform.leave.dto.LeaveDtos.DecisionRequest;
import com.gesmio.platform.leave.entity.LeaveRequest;
import com.gesmio.platform.leave.repository.LeaveRequestRepository;
import com.gesmio.platform.leave.service.LeaveWorkflowService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Approval actions for managers / HR / admins. */
@RestController
@RequestMapping("/api/v1/manager/leave-requests")
public class ManagerLeaveController {

    private final LeaveWorkflowService workflow;
    private final LeaveRequestRepository requests;

    public ManagerLeaveController(LeaveWorkflowService workflow, LeaveRequestRepository requests) {
        this.workflow = workflow;
        this.requests = requests;
    }

    /** All pending requests (the decision endpoint enforces who may actually approve). */
    @GetMapping("/pending")
    public PageResponse<LeaveRequest> pending(
            @PageableDefault(size = 20, sort = "submittedAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return PageResponse.of(requests.findByStatus(LeaveWorkflowService.PENDING, pageable));
    }

    @PostMapping("/{id}/decision")
    public LeaveRequest decide(@PathVariable UUID id, @Valid @RequestBody DecisionRequest body) {
        return workflow.decide(id, body);
    }
}
