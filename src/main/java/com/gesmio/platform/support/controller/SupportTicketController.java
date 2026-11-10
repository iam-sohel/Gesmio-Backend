package com.gesmio.platform.support.controller;

import com.gesmio.platform.common.crud.ReadOnlyCrudController;
import com.gesmio.platform.support.entity.SupportTicket;
import com.gesmio.platform.support.service.SupportTicketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/support/support-tickets")
public class SupportTicketController extends ReadOnlyCrudController<SupportTicket> {

    public SupportTicketController(SupportTicketService service) {
        super(service);
    }
}
