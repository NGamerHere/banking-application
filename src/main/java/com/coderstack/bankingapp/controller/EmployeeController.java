package com.coderstack.bankingapp.controller;

import com.coderstack.bankingapp.Entity.Employee;
import com.coderstack.bankingapp.repository.EmployeeRepository;
import com.coderstack.bankingapp.Entity.Login;
import com.coderstack.bankingapp.security.JwtTokenHandle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRespository;

    public EmployeeController(EmployeeRepository employeeRespository) {
        this.employeeRespository = employeeRespository;
    }

    @PostMapping("/new-employee")
    public ResponseEntity<Map<String, Object>> addingNewEmployee(@RequestBody Employee employee){
        Map<String, Object> response = new HashMap<>();
        employeeRespository.save(employee);
        response.put("message","saved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginHandle(@RequestBody Login login){
        Map<String, Object> response = new HashMap<>();
        if (login==null) {
            return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
            );
        }

        Optional<Employee> employee = employeeRespository.findByEmployeeEmail(login.email);

        if (employee.isPresent()) {
            if(employee.get().verifyEmployeePassword(login.password)){
                Map<String,Object> tokenDetails=new HashMap<>();
                tokenDetails.put("employeeID", employee.get().getEmployeeId());
                tokenDetails.put("role", "employee");

                JwtTokenHandle token=new JwtTokenHandle();
                response.put("token", token.generateToken(tokenDetails));

                return new ResponseEntity<>(response,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }


}
