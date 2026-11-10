package com.gesmio.platform.employees.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.employees.entity.ReportingRelationship;
import com.gesmio.platform.employees.service.ReportingRelationshipService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees/reporting-relationships")
public class ReportingRelationshipController extends CrudController<ReportingRelationship> {

    public ReportingRelationshipController(ReportingRelationshipService service) {
        super(service);
    }
}
