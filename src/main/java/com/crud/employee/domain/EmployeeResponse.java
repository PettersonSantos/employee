package com.crud.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EmployeeResponse {

    private String firstName;
    private String lastName;
    private BigDecimal sallary;
    private Date joinedDate;

    public EmployeeResponse() {
    }

}
