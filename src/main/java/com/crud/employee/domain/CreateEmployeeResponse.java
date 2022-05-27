package com.crud.employee.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class CreateEmployeeResponse {

    private String firstName;
    private String lastName;
    private BigDecimal sallary;
    private Date joinedDate;

}
