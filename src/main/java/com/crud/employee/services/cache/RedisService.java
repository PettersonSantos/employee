package com.crud.employee.services.cache;

import com.crud.employee.domain.Employee;

public interface RedisService {
    Employee storeEmployee(String employeeId, Employee employee);
    Employee retrieveEmployee(String employeeId);
    void clearEmployeeCache(String employeeId);
    void clearAll();
}
