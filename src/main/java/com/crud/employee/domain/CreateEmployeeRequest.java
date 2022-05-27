package com.crud.employee.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class CreateEmployeeRequest {
    private String firstName;
    private String lastName;
    private BigDecimal sallary;
    private Date joinedDate;

    @Override
    public String toString() {
        return "CreateEmployeeRequest [firstName=" + firstName + ", lastName=" + lastName + ", sallary=" + sallary
                + ", joinedDate=" + joinedDate + "]";
    }
}
