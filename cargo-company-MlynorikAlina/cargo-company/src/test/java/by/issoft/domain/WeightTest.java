package by.issoft.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeightTest {
    private Random random;

    @BeforeEach
    public void before() {
        random = new Random();
    }

    @Test
    void of_negative() {
        assertThrows(IllegalArgumentException.class, () -> Weight.of(-1));
    }

    @Test
    void less_true() {
        int randomNotNegativeInt = random.nextInt(Integer.MAX_VALUE - 1);
        Weight weight1 = Weight.of(randomNotNegativeInt);
        Weight weight2 = Weight.of(randomNotNegativeInt + 1);
        assertTrue(weight1.less(weight2));
    }

    @Test
    void less_false() {
        int randomNotNegativeInt = random.nextInt(Integer.MAX_VALUE - 1);
        Weight weight1 = Weight.of(randomNotNegativeInt + 1);
        Weight weight2 = Weight.of(randomNotNegativeInt);
        assertFalse(weight1.less(weight2));
    }

    @Test
    void greater_true() {
        int randomNotNegativeInt = random.nextInt(Integer.MAX_VALUE - 1);
        Weight weight1 = Weight.of(randomNotNegativeInt + 1);
        Weight weight2 = Weight.of(randomNotNegativeInt);
        assertTrue(weight1.greater(weight2));
    }

    @Test
    void greater_false() {
        int randomNotNegativeInt = random.nextInt(Integer.MAX_VALUE - 1);
        Weight weight1 = Weight.of(randomNotNegativeInt);
        Weight weight2 = Weight.of(randomNotNegativeInt + 1);
        assertFalse(weight1.greater(weight2));
    }

    @Test
    void testEquals_true_less1000() {
        int randomNotNegativeInt = random.nextInt(1000);
        Weight weight1 = Weight.of(randomNotNegativeInt);
        Weight weight2 = Weight.of(randomNotNegativeInt);
        assertTrue(weight1.equals(weight2));
        assertTrue(weight1 == weight2);
    }

    @Test
    void testEquals_true_more1000() {
        int randomNotNegativeInt = random.nextInt(Integer.MAX_VALUE - 1001) + 1001;
        Weight weight1 = Weight.of(randomNotNegativeInt);
        Weight weight2 = Weight.of(randomNotNegativeInt);
        assertTrue(weight1.equals(weight2));
        assertFalse(weight1 == weight2);
    }

    @Test
    void testEquals_false() {
        int randomNotNegativeInt = random.nextInt(Integer.MAX_VALUE - 1);
        Weight weight1 = Weight.of(randomNotNegativeInt + 1);
        Weight weight2 = Weight.of(randomNotNegativeInt);
        assertFalse(weight1.equals(weight2));
    }

    @Test
    void intValue() {
        int randomNotNegativeInt = random.nextInt(Integer.MAX_VALUE);
        Weight weight = Weight.of(randomNotNegativeInt);
        assertTrue(weight.intValue() == randomNotNegativeInt);
    }
}