package com.crud.employee.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "employee")
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Date joinedDate;
    private BigDecimal sallary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public BigDecimal getSallary() {
        return sallary;
    }

    public void setSallary(BigDecimal sallary) {
        this.sallary = sallary;
    }
}
