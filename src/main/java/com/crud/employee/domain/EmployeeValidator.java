package com.crud.employee.domain;

import org.springframework.util.Assert;

public class EmployeeValidator {

    public static void validateCreateEmployeeRequest(CreateEmployeeRequest request) {
        Assert.notNull(request, "Create Employee Request cannot be null");
        Assert.hasText(request.getFirstName(), "First name is required");
        Assert.notNull(request.getSallary(), "Sallary is required");
    }

}
