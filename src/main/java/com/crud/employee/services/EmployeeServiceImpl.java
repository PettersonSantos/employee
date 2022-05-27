package com.crud.employee.services;

import com.crud.employee.domain.CreateEmployeeRequest;
import com.crud.employee.domain.CreateEmployeeResponse;
import com.crud.employee.domain.Employee;
import com.crud.employee.domain.EmployeeResponse;
import com.crud.employee.domain.exception.NotFoundException;
import com.crud.employee.domain.transformer.EmployeeTranformer;
import com.crud.employee.repositories.EmployeeRepository;
import com.crud.employee.services.cache.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    final static Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        logger.info("Inside create employee service");

        Employee employee = null;
        CreateEmployeeResponse response = null;

        try {

            employee = EmployeeTranformer.transformEmployeeRequestToDto(createEmployeeRequest);
            employee = employeeRepository.save(employee);

            redisService.storeEmployee(employee.getId(), employee);

            response = EmployeeTranformer.transformEmployeeDtoToResponse(employee);

        } catch (HttpServerErrorException hse) {
            logger.error("Server Error found : " + hse.getMessage().toString());
            hse.printStackTrace();
        } catch (Exception e) {
            logger.error("Error found : " + e.getMessage().toString());
            e.printStackTrace();
        }

        logger.info("Successfully saved new employee");

        return response;
    }

    @Override
    public EmployeeResponse retrieveEmployeeById(String employeeId) {
        logger.info("Inside retrieve employee by id, service");

        Optional<Employee> optionalEmployee;
        Employee employee = null;
        EmployeeResponse employeeResponse = null;

        employee = redisService.retrieveEmployee(employeeId);

        if(employee == null) {

            optionalEmployee = employeeRepository.findById(employeeId);

            if (optionalEmployee.isPresent()) {
                employee = optionalEmployee.get();
            } else {
                throw new NotFoundException("INVALID_EMPLOYEE_ID", "Employee not found for the given id");
            }
        }

        employeeResponse = EmployeeTranformer.employeeToEmployeeResponse(employee);

        logger.info("Successfully returned employee by id");
        return employeeResponse;
    }
}
