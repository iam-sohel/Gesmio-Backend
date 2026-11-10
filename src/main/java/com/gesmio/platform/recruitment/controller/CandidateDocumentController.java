package com.gesmio.platform.recruitment.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.recruitment.entity.CandidateDocument;
import com.gesmio.platform.recruitment.service.CandidateDocumentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitment/candidate-documents")
public class CandidateDocumentController extends CrudController<CandidateDocument> {

    public CandidateDocumentController(CandidateDocumentService service) {
        super(service);
    }
}
