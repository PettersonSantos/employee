package com.crud.employee.domain.transformer;

import com.crud.employee.domain.CreateEmployeeRequest;
import com.crud.employee.domain.CreateEmployeeResponse;
import com.crud.employee.domain.Employee;
import com.crud.employee.domain.EmployeeResponse;

import java.util.UUID;

public class EmployeeTranformer {

    public static Employee transformEmployeeRequestToDto(CreateEmployeeRequest createEmployeeRequest) {

        Employee employee = new Employee();
        employee.setFirstName(createEmployeeRequest.getFirstName());
        employee.setJoinedDate(createEmployeeRequest.getJoinedDate());
        employee.setLastName(createEmployeeRequest.getLastName());
        employee.setSallary(createEmployeeRequest.getSallary());
        employee.setId(UUID.randomUUID().toString());

        return employee;
    }

    public static CreateEmployeeResponse transformEmployeeDtoToResponse(Employee employee) {

        CreateEmployeeResponse response = new CreateEmployeeResponse();
        response.setFirstName(employee.getFirstName());
        response.setJoinedDate(employee.getJoinedDate());
        response.setLastName(employee.getLastName());
        response.setSallary(employee.getSallary());

        return response;
    }

    public static EmployeeResponse employeeToEmployeeResponse(Employee employee) {

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setJoinedDate(employee.getJoinedDate());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setSallary(employee.getSallary());

        return employeeResponse;
    }
}
