package com.gesmio.platform.employees.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.employees.entity.EmploymentRecord;
import com.gesmio.platform.employees.service.EmploymentRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees/employment-records")
public class EmploymentRecordController extends CrudController<EmploymentRecord> {

    public EmploymentRecordController(EmploymentRecordService service) {
        super(service);
    }
}
