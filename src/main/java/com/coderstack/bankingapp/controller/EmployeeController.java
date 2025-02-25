package com.coderstack.bankingapp.controller;

import com.coderstack.bankingapp.Entity.Employee;
import com.coderstack.bankingapp.repository.EmployeeRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRespository;

    public EmployeeController(EmployeeRepository employeeRespository) {
        this.employeeRespository = employeeRespository;
    }

    @PostMapping("/new-employee")
    public ResponseEntity<Map<String, Object>> addingNewEmployee(Employee employee){
        Map<String, Object> response = new HashMap<>();
        employeeRespository.save(employee);
        response.put("message","saved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
