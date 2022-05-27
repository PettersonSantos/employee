package com.crud.employee.controller;

import com.crud.employee.controller.common.HTTPResponseHandler;
import com.crud.employee.controller.common.RequestMappings;
import com.crud.employee.domain.CreateEmployeeRequest;
import com.crud.employee.domain.CreateEmployeeResponse;
import com.crud.employee.domain.EmployeeResponse;
import com.crud.employee.domain.EmployeeValidator;
import com.crud.employee.services.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RequestMappings.EMPLOYEES)
public class EmployeeController extends HTTPResponseHandler {
    final static Logger logger = LogManager.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = RequestMappings.CREATE_EMPLOYEE, method = RequestMethod.POST)
    public @ResponseBody CreateEmployeeResponse createEmployee(@RequestBody CreateEmployeeRequest request) {

        EmployeeValidator.validateCreateEmployeeRequest(request);

        logger.info("Inside > create new employee endpoint ");

        return employeeService.createEmployee(request);

    }
    @RequestMapping(value = RequestMappings.RETRIVE_EMPLOYEE_BY_ID, method = RequestMethod.GET)
    public @ResponseBody EmployeeResponse retriveEmployeeById(
            @PathVariable(value = "employee-id", required = true) String employeeId) {

        logger.info("Inside > retrieve employee by id endpoint ");

        return employeeService.retrieveEmployeeById(employeeId);

    }

}
