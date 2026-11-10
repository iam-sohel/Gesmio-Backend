package com.gesmio.platform.support.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.support.entity.SupportTicket;
import com.gesmio.platform.support.repository.SupportTicketRepository;
import org.springframework.stereotype.Service;

@Service
public class SupportTicketService extends CrudService<SupportTicket> {

    public SupportTicketService(SupportTicketRepository repository, AuditService auditService) {
        super(repository, repository, SupportTicket.class, "support_tickets", auditService);
    }
}
