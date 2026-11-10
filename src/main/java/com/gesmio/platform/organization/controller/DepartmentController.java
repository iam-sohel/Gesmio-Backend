package com.gesmio.platform.organization.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.organization.entity.Department;
import com.gesmio.platform.organization.service.DepartmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization/departments")
public class DepartmentController extends CrudController<Department> {

    public DepartmentController(DepartmentService service) {
        super(service);
    }
}
