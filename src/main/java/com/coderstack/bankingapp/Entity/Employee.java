package com.coderstack.bankingapp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    private String employeeName;
    private String employeeUsername;
    private String employeePassword;
    private String employeeEmail;
    private String employeePhone;
    private String employeeAddress;
    private LocalDate employeeDateOfBirth;

    public Employee() {}

    public Integer getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getEmployeeUsername() {
        return employeeUsername;
    }
    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public boolean verifyEmployeePassword(String employeePassword) {
        return this.employeePassword.equals(employeePassword);
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
    public String getEmployeePhone() {
        return employeePhone;
    }
    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }
    public String getEmployeeAddress() {
        return employeeAddress;
    }
    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public LocalDate getEmployeeDateOfBirth() {
        return employeeDateOfBirth;
    }
    
    public void setEmployeeDateOfBirth(LocalDate employeeDateOfBirth) {
        this.employeeDateOfBirth = employeeDateOfBirth;
    }
    
}