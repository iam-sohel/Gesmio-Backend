package com.gesmio.platform.recruitment.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.recruitment.entity.Interview;
import com.gesmio.platform.recruitment.service.InterviewService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitment/interviews")
public class InterviewController extends CrudController<Interview> {

    public InterviewController(InterviewService service) {
        super(service);
    }
}
