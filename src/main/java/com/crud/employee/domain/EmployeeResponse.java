package com.crud.employee.domain;

import java.math.BigDecimal;
import java.util.Date;

public class EmployeeResponse {

    private String firstName;

    private String lastName;

    private BigDecimal sallary;

    private Date joinedDate;

    public EmployeeResponse() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSallary() {
        return sallary;
    }

    public void setSallary(BigDecimal sallary) {
        this.sallary = sallary;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }
}
