package com.gesmio.platform.employees.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.employees.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees/employees")
public class EmployeeController extends CrudController<Employee> {

    public EmployeeController(EmployeeService service) {
        super(service);
    }
}
