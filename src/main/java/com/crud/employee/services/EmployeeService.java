package com.crud.employee.services;

import com.crud.employee.domain.CreateEmployeeRequest;
import com.crud.employee.domain.CreateEmployeeResponse;
import com.crud.employee.domain.EmployeeResponse;

public interface EmployeeService {
    CreateEmployeeResponse createEmployee(CreateEmployeeRequest createEmployeeRequest);
    EmployeeResponse retrieveEmployeeById(String employeeId);
}
