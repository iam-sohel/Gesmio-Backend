package com.gesmio.platform.employees.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.employees.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends CrudService<Employee> {

    public EmployeeService(EmployeeRepository repository, AuditService auditService) {
        super(repository, repository, Employee.class, "employees", auditService);
    }
}
