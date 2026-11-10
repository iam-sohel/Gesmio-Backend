package com.gesmio.platform.support.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.support.entity.SupportTicketComment;
import com.gesmio.platform.support.repository.SupportTicketCommentRepository;
import org.springframework.stereotype.Service;

@Service
public class SupportTicketCommentService extends CrudService<SupportTicketComment> {

    public SupportTicketCommentService(SupportTicketCommentRepository repository, AuditService auditService) {
        super(repository, repository, SupportTicketComment.class, "support_ticket_comments", auditService);
    }
}
