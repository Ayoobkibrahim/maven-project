package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Hello World from Spring Boot Web App!";
    }

    @GetMapping("/add")
    public String addNumbers(@RequestParam int a, @RequestParam int b) {
        int result = a + b;
        return "Sum of " + a + " and " + b + " is: " + result;
    }
}
