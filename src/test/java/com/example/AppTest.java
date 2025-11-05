package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddEndpoint() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/add?a=5&b=3", String.class);
        assertTrue(response.getBody().contains("8"), "Sum should be 8");
    }
}
