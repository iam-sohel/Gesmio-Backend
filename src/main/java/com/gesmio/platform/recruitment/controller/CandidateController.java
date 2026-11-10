package com.gesmio.platform.recruitment.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.recruitment.entity.Candidate;
import com.gesmio.platform.recruitment.service.CandidateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitment/candidates")
public class CandidateController extends CrudController<Candidate> {

    public CandidateController(CandidateService service) {
        super(service);
    }
}
