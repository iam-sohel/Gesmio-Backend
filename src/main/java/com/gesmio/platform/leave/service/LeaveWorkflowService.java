package com.gesmio.platform.leave.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.exception.BusinessException;
import com.gesmio.platform.common.exception.ResourceNotFoundException;
import com.gesmio.platform.common.security.SecurityUtils;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.employees.entity.ReportingRelationship;
import com.gesmio.platform.employees.repository.EmployeeRepository;
import com.gesmio.platform.employees.repository.ReportingRelationshipRepository;
import com.gesmio.platform.leave.dto.LeaveDtos.ApplyLeaveRequest;
import com.gesmio.platform.leave.dto.LeaveDtos.DecisionRequest;
import com.gesmio.platform.leave.entity.LeaveApproval;
import com.gesmio.platform.leave.entity.LeaveBalance;
import com.gesmio.platform.leave.entity.LeaveBalanceTransaction;
import com.gesmio.platform.leave.entity.LeavePolicy;
import com.gesmio.platform.leave.entity.LeaveRequest;
import com.gesmio.platform.leave.repository.LeaveApprovalRepository;
import com.gesmio.platform.leave.repository.LeaveBalanceRepository;
import com.gesmio.platform.leave.repository.LeaveBalanceTransactionRepository;
import com.gesmio.platform.leave.repository.LeavePolicyRepository;
import com.gesmio.platform.leave.repository.LeaveRequestRepository;
import com.gesmio.platform.notifications.service.NotificationPublisher;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Leave lifecycle: apply -> (manager/HR) approve|reject -> balance update; employee may cancel. */
@Service
@Transactional
public class LeaveWorkflowService {

    public static final String PENDING = "PENDING";
    public static final String APPROVED = "APPROVED";
    public static final String REJECTED = "REJECTED";
    public static final String CANCELLED = "CANCELLED";

    private final LeaveRequestRepository requests;
    private final LeavePolicyRepository policies;
    private final LeaveBalanceRepository balances;
    private final LeaveBalanceTransactionRepository transactions;
    private final LeaveApprovalRepository approvals;
    private final EmployeeRepository employees;
    private final ReportingRelationshipRepository reporting;
    private final NotificationPublisher notifications;
    private final AuditService audit;

    public LeaveWorkflowService(LeaveRequestRepository requests, LeavePolicyRepository policies,
                                LeaveBalanceRepository balances, LeaveBalanceTransactionRepository transactions,
                                LeaveApprovalRepository approvals, EmployeeRepository employees,
                                ReportingRelationshipRepository reporting, NotificationPublisher notifications,
                                AuditService audit) {
        this.requests = requests;
        this.policies = policies;
        this.balances = balances;
        this.transactions = transactions;
        this.approvals = approvals;
        this.employees = employees;
        this.reporting = reporting;
        this.notifications = notifications;
        this.audit = audit;
    }

    public LeaveRequest apply(Employee employee, ApplyLeaveRequest dto) {
        if (dto.endDate().isBefore(dto.startDate())) {
            throw new BusinessException("End date cannot be before start date");
        }
        LeavePolicy policy = policies.findById(dto.leavePolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave policy not found"));
        if (!Boolean.TRUE.equals(policy.getIsActive())) {
            throw new BusinessException("Leave policy is not active");
        }
        BigDecimal workingDays = BigDecimal.valueOf(countWeekdays(dto.startDate(), dto.endDate()));
        if (workingDays.signum() == 0) {
            throw new BusinessException("Selected range contains no working days");
        }
        BigDecimal days = dto.requestedDays() != null ? dto.requestedDays() : workingDays;
        if (days.signum() <= 0 || days.compareTo(workingDays) > 0) {
            throw new BusinessException("requestedDays must be between 0 and " + workingDays);
        }
        if (dto.startDate().getYear() != dto.endDate().getYear()) {
            throw new BusinessException("A leave request cannot span two calendar years; submit two requests");
        }
        boolean overlap = requests.existsByEmployeeIdAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                employee.getId(), List.of(PENDING, APPROVED), dto.endDate(), dto.startDate());
        if (overlap) {
            throw new BusinessException("You already have a pending or approved leave overlapping these dates");
        }
        LeaveBalance balance = ensureBalance(employee.getId(), policy, dto.startDate().getYear());
        if (Boolean.TRUE.equals(policy.getIsPaid()) && balance.getAvailableDays().compareTo(days) < 0) {
            throw new BusinessException("Insufficient leave balance: available " + balance.getAvailableDays()
                    + ", requested " + days);
        }
        LeaveRequest request = new LeaveRequest();
        request.setEmployeeId(employee.getId());
        request.setLeavePolicyId(policy.getId());
        request.setStartDate(dto.startDate());
        request.setEndDate(dto.endDate());
        request.setRequestedDays(days);
        request.setReason(dto.reason());
        request.setStatus(PENDING);
        final LeaveRequest saved = requests.save(request);

        managerUserId(employee.getId()).ifPresent(managerUser -> notifications.notify(managerUser, "LEAVE_REQUEST",
                "New leave request", employee.getEmployeeCode() + " requested " + days + " day(s) from "
                        + dto.startDate() + " to " + dto.endDate(), "leave_requests", saved.getId()));
        audit.record("LEAVE_APPLY", "leave_requests", saved.getId(), "SUCCESS");
        return saved;
    }

    public LeaveRequest decide(UUID requestId, DecisionRequest dto) {
        String decision = dto.decision().trim().toUpperCase();
        if (!decision.equals(APPROVED) && !decision.equals(REJECTED)) {
            throw new BusinessException("decision must be APPROVED or REJECTED");
        }
        LeaveRequest request = find(requestId);
        if (!PENDING.equals(request.getStatus())) {
            throw new BusinessException("Only PENDING requests can be decided (current: " + request.getStatus() + ")");
        }
        UUID approverUser = SecurityUtils.currentUserId();
        Employee requester = employees.findById(request.getEmployeeId()).orElseThrow();
        if (approverUser.equals(requester.getUserId()) && !SecurityUtils.hasRole("ADMIN")) {
            throw new BusinessException("You cannot decide your own leave request");
        }
        boolean isManager = managerUserId(requester.getId()).map(approverUser::equals).orElse(false);
        if (!isManager && !SecurityUtils.isPrivileged()) {
            throw new BusinessException("Only the reporting manager, HR or an admin can decide this request");
        }

        LeaveApproval approval = new LeaveApproval();
        approval.setLeaveRequestId(request.getId());
        approval.setApproverUserId(approverUser);
        approval.setApprovalLevel(1);
        approval.setDecision(decision);
        approval.setComments(dto.comments());
        approval.setActedAt(Instant.now());
        approvals.save(approval);

        request.setStatus(decision);
        LeavePolicy policy = policies.findById(request.getLeavePolicyId()).orElseThrow();
        if (decision.equals(APPROVED) && Boolean.TRUE.equals(policy.getIsPaid())) {
            LeaveBalance balance = ensureBalance(requester.getId(), policy, request.getStartDate().getYear());
            if (balance.getAvailableDays().compareTo(request.getRequestedDays()) < 0) {
                throw new BusinessException("Employee no longer has enough balance to approve this request");
            }
            adjustBalance(balance, request, request.getRequestedDays(), "DEBIT");
        }
        notifications.notify(requester.getUserId(), "LEAVE_DECISION", "Leave request " + decision.toLowerCase(),
                "Your leave from " + request.getStartDate() + " to " + request.getEndDate() + " was "
                        + decision.toLowerCase() + (dto.comments() != null ? ": " + dto.comments() : ""),
                "leave_requests", request.getId());
        audit.record("LEAVE_" + decision, "leave_requests", request.getId(), "SUCCESS");
        return request;
    }

    public LeaveRequest cancel(Employee employee, UUID requestId) {
        LeaveRequest request = find(requestId);
        if (!request.getEmployeeId().equals(employee.getId())) {
            throw new ResourceNotFoundException("Leave request not found: " + requestId);
        }
        if (PENDING.equals(request.getStatus())) {
            request.setStatus(CANCELLED);
        } else if (APPROVED.equals(request.getStatus())) {
            if (!request.getStartDate().isAfter(LocalDate.now())) {
                throw new BusinessException("Leave that has already started cannot be cancelled; contact HR");
            }
            request.setStatus(CANCELLED);
            LeavePolicy policy = policies.findById(request.getLeavePolicyId()).orElseThrow();
            if (Boolean.TRUE.equals(policy.getIsPaid())) {
                LeaveBalance balance = ensureBalance(employee.getId(), policy, request.getStartDate().getYear());
                adjustBalance(balance, request, request.getRequestedDays(), "CREDIT");
            }
        } else {
            throw new BusinessException("Request is already " + request.getStatus());
        }
        audit.record("LEAVE_CANCEL", "leave_requests", request.getId(), "SUCCESS");
        return request;
    }

    @Transactional(readOnly = true)
    public List<LeaveBalance> balances(UUID employeeId, int year) {
        return balances.findByEmployeeIdAndLeaveYear(employeeId, year);
    }

    // ---- helpers -----------------------------------------------------------------------------

    private LeaveRequest find(UUID id) {
        return requests.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leave request not found: " + id));
    }

    private java.util.Optional<UUID> managerUserId(UUID employeeId) {
        return reporting.findFirstByEmployeeIdAndEffectiveToIsNull(employeeId)
                .map(ReportingRelationship::getManagerEmployeeId)
                .flatMap(employees::findById)
                .map(Employee::getUserId);
    }

    private LeaveBalance ensureBalance(UUID employeeId, LeavePolicy policy, int year) {
        return balances.findByEmployeeIdAndLeavePolicyIdAndLeaveYear(employeeId, policy.getId(), year)
                .orElseGet(() -> {
                    LeaveBalance b = new LeaveBalance();
                    b.setEmployeeId(employeeId);
                    b.setLeavePolicyId(policy.getId());
                    b.setLeaveYear(year);
                    b.setOpeningBalance(policy.getAnnualEntitlement());
                    b.setAccruedDays(BigDecimal.ZERO);
                    b.setUsedDays(BigDecimal.ZERO);
                    b.setAvailableDays(policy.getAnnualEntitlement());
                    return balances.save(b);
                });
    }

    private void adjustBalance(LeaveBalance balance, LeaveRequest request, BigDecimal days, String type) {
        BigDecimal signed = type.equals("DEBIT") ? days : days.negate();
        balance.setUsedDays(balance.getUsedDays().add(signed));
        balance.setAvailableDays(balance.getOpeningBalance().add(balance.getAccruedDays()).subtract(balance.getUsedDays()));
        LeaveBalanceTransaction tx = new LeaveBalanceTransaction();
        tx.setLeaveBalanceId(balance.getId());
        tx.setLeaveRequestId(request.getId());
        tx.setTransactionType(type);
        tx.setDays(days);
        transactions.save(tx);
    }

    static long countWeekdays(LocalDate start, LocalDate end) {
        return start.datesUntil(end.plusDays(1))
                .filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();
    }
}
