package com.coderstack.bankingapp.controller;

import com.coderstack.bankingapp.Entity.Login;
import com.coderstack.bankingapp.Entity.User;
import com.coderstack.bankingapp.repository.UserRepository;
import com.coderstack.bankingapp.security.TokenManager;

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
@RequestMapping("/login")
public class LoginController {


    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> authentication(@RequestBody Login login) {
        Map<String, Object> response = new HashMap<>();
        if (login == null) {
            response.put("error", "invalid login");
            response.put("status", "failure");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<User> n=userRepository.findByEmail(login.email);
       if(n.isPresent()) {
           if(n.get().verifyPassword(login.password)){

            Map<String, Object> tokenData=new HashMap<>();
            Map.put("user_id",n.get().getId());

               TokenManager newToken=new TokenManager();

               response.put("token", newToken.generateToken(response));
               
               return new ResponseEntity<>(response, HttpStatus.OK);
           }else{
            response.put("error", "invalid username     and password");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
           }
        }else{
            response.put("error", "user not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
}
