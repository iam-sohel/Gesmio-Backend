package com.gesmio.platform.recruitment.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.recruitment.entity.InterviewFeedback;
import com.gesmio.platform.recruitment.service.InterviewFeedbackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitment/interview-feedback")
public class InterviewFeedbackController extends CrudController<InterviewFeedback> {

    public InterviewFeedbackController(InterviewFeedbackService service) {
        super(service);
    }
}
