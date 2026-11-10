package com.gesmio.platform.support.controller;

import com.gesmio.platform.common.crud.ReadOnlyCrudController;
import com.gesmio.platform.support.entity.SupportTicketComment;
import com.gesmio.platform.support.service.SupportTicketCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/support/support-ticket-comments")
public class SupportTicketCommentController extends ReadOnlyCrudController<SupportTicketComment> {

    public SupportTicketCommentController(SupportTicketCommentService service) {
        super(service);
    }
}
