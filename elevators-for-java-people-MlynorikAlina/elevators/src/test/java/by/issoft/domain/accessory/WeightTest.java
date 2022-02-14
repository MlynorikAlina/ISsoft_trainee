package by.issoft.domain.accessory;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WeightTest {

    private static final Random rand = new Random();

    @Test
    void of_negative() {
        assertThrows(IllegalArgumentException.class, () -> Weight.of(-1));
    }

    @Test
    void of_same() {
        int someInt = rand.nextInt(500);
        assertSame(Weight.of(someInt), Weight.of(someInt));
    }

    @Test
    void of_notSame() {
        int someInt = rand.nextInt(500) + 3000;
        assertNotSame(Weight.of(someInt), Weight.of(someInt));
    }

    @Test
    void less_true() {
        int someInt = rand.nextInt(500);
        assertTrue(Weight.of(someInt).less(Weight.of(1+someInt)));
    }

    @Test
    void less_false() {
        int someInt = rand.nextInt(500);
        assertFalse(Weight.of(someInt).less(Weight.of(someInt)));
        assertFalse(Weight.of(someInt + 1).less(Weight.of(someInt)));
    }

    @Test
    void lessOrEquals_true() {
        int someInt = rand.nextInt(500);
        assertTrue(Weight.of(someInt).lessOrEquals(Weight.of(someInt)));
        assertTrue(Weight.of(someInt).lessOrEquals(Weight.of(someInt + 1)));
    }

    @Test
    void lessOrEquals_false() {
        int someInt = rand.nextInt(500);
        assertFalse(Weight.of(someInt + 1).lessOrEquals(Weight.of(someInt)));
    }

    @Test
    void greater_true() {
        int someInt = rand.nextInt(500);
        assertTrue(Weight.of(someInt + 1).greater(Weight.of(someInt)));
    }

    @Test
    void greater_false() {
        int someInt = rand.nextInt(500);
        assertFalse(Weight.of(someInt).greater(Weight.of(someInt)));
        assertFalse(Weight.of(someInt).greater(Weight.of(someInt + 1)));
    }

    @Test
    void greaterOrEquals_true() {
        int someInt = rand.nextInt(500);
        assertTrue(Weight.of(someInt).greaterOrEquals(Weight.of(someInt)));
        assertTrue(Weight.of(someInt + 1).greaterOrEquals(Weight.of(someInt)));
    }

    @Test
    void greaterOrEquals_false() {
        int someInt = rand.nextInt(500);
        assertFalse(Weight.of(someInt).greaterOrEquals(Weight.of(someInt + 1)));
    }

    @Test
    void intValue() {
        int someInt = rand.nextInt(500);
        assertEquals(someInt, Weight.of(someInt).intValue());
    }

    @Test
    void sum() {
        int someInt1 = rand.nextInt(500);
        int someInt2 = rand.nextInt(500);
        assertEquals(someInt1 + someInt2, Weight.sum(Weight.of(someInt1), Weight.of(someInt2)).intValue());
    }

    @Test
    void substract_success() {
        int someInt1 = rand.nextInt(500);
        int someInt2 = someInt1 + rand.nextInt(500);
        assertEquals(someInt2 - someInt1, Weight.subtract(Weight.of(someInt2), Weight.of(someInt1)).intValue());

    }

    @Test
    void substract_fail() {
        int someInt1 = 501;
        int someInt2 = rand.nextInt(500);
        assertThrows(IllegalArgumentException.class, () -> Weight.subtract(Weight.of(someInt2), Weight.of(someInt1)));

    }
}