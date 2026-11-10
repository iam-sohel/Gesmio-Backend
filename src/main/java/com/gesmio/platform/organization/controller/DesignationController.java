package com.gesmio.platform.organization.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.organization.entity.Designation;
import com.gesmio.platform.organization.service.DesignationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization/designations")
public class DesignationController extends CrudController<Designation> {

    public DesignationController(DesignationService service) {
        super(service);
    }
}
