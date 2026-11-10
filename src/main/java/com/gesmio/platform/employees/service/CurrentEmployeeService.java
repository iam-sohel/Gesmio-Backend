package com.gesmio.platform.employees.service;

import com.gesmio.platform.common.exception.BusinessException;
import com.gesmio.platform.common.security.SecurityUtils;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.employees.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

/** Resolves the employee profile linked to the logged-in user. */
@Service
public class CurrentEmployeeService {

    private final EmployeeRepository employees;

    public CurrentEmployeeService(EmployeeRepository employees) {
        this.employees = employees;
    }

    public Employee require() {
        return employees.findByUserId(SecurityUtils.currentUserId())
                .orElseThrow(() -> new BusinessException("No employee profile is linked to your user account"));
    }
}
