package com.gesmio.platform.recruitment.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.recruitment.entity.InterviewFeedback;
import com.gesmio.platform.recruitment.repository.InterviewFeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class InterviewFeedbackService extends CrudService<InterviewFeedback> {

    public InterviewFeedbackService(InterviewFeedbackRepository repository, AuditService auditService) {
        super(repository, repository, InterviewFeedback.class, "interview_feedback", auditService);
    }
}
