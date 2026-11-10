package com.gesmio.platform.recruitment.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.recruitment.entity.JobApplication;
import com.gesmio.platform.recruitment.service.JobApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitment/job-applications")
public class JobApplicationController extends CrudController<JobApplication> {

    public JobApplicationController(JobApplicationService service) {
        super(service);
    }
}
