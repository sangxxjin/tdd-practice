package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleCalculatorTest {

    @Test
    @DisplayName("1 + 2 = 3")
    public void testPlus() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int result = simpleCalculator.plus(1,2);
        assertEquals(3, result);
    }

    @Test
    @DisplayName("7 * 3 = 21")
    public void testMultiply() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int result = simpleCalculator.multiply(7,3);
        assertEquals(21, result);
    }

    @Test
    @DisplayName("10 - 6 = 4")
    public void testMinus() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int result = simpleCalculator.minus(10,6);
        assertEquals(4, result);
    }

    @Test
    @DisplayName("33 / 6 = 5")
    public void testDivide() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int result = simpleCalculator.divide(33,6);
        assertEquals(5, result);
    }

    @Test
    @DisplayName("33 % 6 = 3")
    public void testReminder() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int result = simpleCalculator.reminder(33,6);
        assertEquals(3, result);
    }

}
