package com.gesmio.platform.recruitment.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.recruitment.entity.JobOffer;
import com.gesmio.platform.recruitment.service.JobOfferService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitment/job-offers")
public class JobOfferController extends CrudController<JobOffer> {

    public JobOfferController(JobOfferService service) {
        super(service);
    }
}
