package com.gesmio.platform.recruitment.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.recruitment.entity.JobOpening;
import com.gesmio.platform.recruitment.service.JobOpeningService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitment/job-openings")
public class JobOpeningController extends CrudController<JobOpening> {

    public JobOpeningController(JobOpeningService service) {
        super(service);
    }
}
