package com.coderstack.bankingapp.repository;

import com.coderstack.bankingapp.Entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    public Optional<Employee> findByEmployeeUsername(String employeeUsername);

    public Optional<Employee> findByEmployeeEmail(String employeeEmail);

}
