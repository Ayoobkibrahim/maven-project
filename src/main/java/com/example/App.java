package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Hello World Maven Project (Spring Boot Version)!";
    }

    @GetMapping("/add")
    public String add(@RequestParam int a, @RequestParam int b) {
        return "Sum is: " + (a + b);
    }
}
