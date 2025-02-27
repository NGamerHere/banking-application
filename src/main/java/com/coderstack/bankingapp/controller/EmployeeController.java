package com.coderstack.bankingapp.controller;

import com.coderstack.bankingapp.Entity.Employee;
import com.coderstack.bankingapp.repository.EmployeeRepository;
import com.coderstack.bankingapp.Entity.Login;
import com.coderstack.bankingapp.security.JwtTokenHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final JwtTokenHandle jwtTokenHandle;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, JwtTokenHandle jwtTokenHandle) {
        this.employeeRepository = employeeRepository;
        this.jwtTokenHandle = jwtTokenHandle;
    }

    @PostMapping("/new-employee")
    public ResponseEntity<Map<String, Object>> addingNewEmployee(@RequestBody Employee employee) {
        Map<String, Object> response = new HashMap<>();
        employeeRepository.save(employee);
        response.put("message", "saved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginHandle(@RequestBody Login login) {
        Map<String, Object> response = new HashMap<>();

        if (login == null || login.email == null || login.password == null) {
            response.put("error", "Invalid request data");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Employee> employee = employeeRepository.findByEmployeeEmail(login.email);

        if (employee.isPresent() && employee.get().verifyEmployeePassword(login.password)) {
            Map<String, Object> tokenDetails = new HashMap<>();
            tokenDetails.put("employeeID", employee.get().getEmployeeId());
            tokenDetails.put("role", "employee");

            String token = jwtTokenHandle.generateToken(tokenDetails);

            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("error", "Invalid email or password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/verify-token")
    public ResponseEntity<Map<String , Object>> verifyHandle(@RequestHeader(name = "Authorization") String token){
        if(!jwtTokenHandle.isTokenExpired(token)){
            Map<String,Object>response= jwtTokenHandle.getTokenData(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            Map<String, Object> response = new HashMap<>();
            response.put("error","token expired");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
