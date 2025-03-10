package com.coderstack.bankingapp.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.coderstack.bankingapp.Entity.Greeting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Value("${jwt.secret}")
    private String jwtSecret;

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println(counter.get() + ": " + name);
        System.out.println("the key is generated :" + jwtSecret);
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
