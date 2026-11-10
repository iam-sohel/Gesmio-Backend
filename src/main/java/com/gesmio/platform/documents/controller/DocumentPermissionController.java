package com.gesmio.platform.documents.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.documents.entity.DocumentPermission;
import com.gesmio.platform.documents.service.DocumentPermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents/document-permissions")
public class DocumentPermissionController extends CrudController<DocumentPermission> {

    public DocumentPermissionController(DocumentPermissionService service) {
        super(service);
    }
}
