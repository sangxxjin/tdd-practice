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

}
