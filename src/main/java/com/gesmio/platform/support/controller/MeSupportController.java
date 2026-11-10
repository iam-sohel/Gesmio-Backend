package com.gesmio.platform.support.controller;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.PageResponse;
import com.gesmio.platform.common.exception.ResourceNotFoundException;
import com.gesmio.platform.common.security.SecurityUtils;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.employees.service.CurrentEmployeeService;
import com.gesmio.platform.support.dto.SupportDtos.CommentRequest;
import com.gesmio.platform.support.dto.SupportDtos.CreateTicketRequest;
import com.gesmio.platform.support.entity.SupportTicket;
import com.gesmio.platform.support.entity.SupportTicketComment;
import com.gesmio.platform.support.repository.SupportTicketCommentRepository;
import com.gesmio.platform.support.repository.SupportTicketRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Employees raise and follow their own tickets. Internal comments are never shown here. */
@RestController
@RequestMapping("/api/v1/me/support-tickets")
@Transactional
public class MeSupportController {

    private final SupportTicketRepository tickets;
    private final SupportTicketCommentRepository comments;
    private final CurrentEmployeeService currentEmployee;
    private final AuditService audit;

    public MeSupportController(SupportTicketRepository tickets, SupportTicketCommentRepository comments,
                               CurrentEmployeeService currentEmployee, AuditService audit) {
        this.tickets = tickets;
        this.comments = comments;
        this.currentEmployee = currentEmployee;
        this.audit = audit;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public PageResponse<SupportTicket> mine(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return PageResponse.of(tickets.findByRequesterEmployeeId(currentEmployee.require().getId(), pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupportTicket create(@Valid @RequestBody CreateTicketRequest body) {
        Employee e = currentEmployee.require();
        SupportTicket t = new SupportTicket();
        t.setTicketNumber("TKT-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
        t.setRequesterEmployeeId(e.getId());
        t.setCategoryId(body.categoryId());
        t.setSubject(body.subject());
        t.setDescription(body.description());
        if (body.priority() != null && !body.priority().isBlank()) {
            t.setPriority(body.priority().toUpperCase());
        }
        t = tickets.save(t);
        audit.record("CREATE", "support_tickets", t.getId(), "SUCCESS");
        return t;
    }

    @GetMapping("/{id}/comments")
    @Transactional(readOnly = true)
    public List<SupportTicketComment> comments(@PathVariable UUID id) {
        own(id);
        return comments.findByTicketIdOrderByCreatedAtAsc(id).stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsInternal())).toList();
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public SupportTicketComment addComment(@PathVariable UUID id, @Valid @RequestBody CommentRequest body) {
        own(id);
        SupportTicketComment c = new SupportTicketComment();
        c.setTicketId(id);
        c.setAuthorUserId(SecurityUtils.currentUserId());
        c.setComment(body.comment());
        c.setIsInternal(false);
        return comments.save(c);
    }

    private SupportTicket own(UUID id) {
        SupportTicket t = tickets.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        if (!t.getRequesterEmployeeId().equals(currentEmployee.require().getId())) {
            throw new ResourceNotFoundException("Ticket not found");
        }
        return t;
    }
}
