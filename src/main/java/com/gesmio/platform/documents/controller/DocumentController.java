package com.gesmio.platform.documents.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.documents.entity.Document;
import com.gesmio.platform.documents.service.DocumentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents/documents")
public class DocumentController extends CrudController<Document> {

    public DocumentController(DocumentService service) {
        super(service);
    }
}
