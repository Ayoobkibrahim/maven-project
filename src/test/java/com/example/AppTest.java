package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testAdd() {
        App app = new App();
        int result = app.add(5, 3);
        assertEquals(8, result, "5 + 3 should equal 8");
    }

    @Test
    public void testAddNegative() {
        App app = new App();
        int result = app.add(-2, -3);
        assertEquals(-5, result, "-2 + -3 should equal -5");
    }
}