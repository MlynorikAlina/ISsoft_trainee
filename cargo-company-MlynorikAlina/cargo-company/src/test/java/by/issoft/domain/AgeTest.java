package by.issoft.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgeTest {
    private Random random;
    private final static int MAX_AGE_INT = Age.MAX_AGE.intValue();
    private final static int MIN_AGE_INT = Age.MIN_AGE.intValue();

    @BeforeEach
    public void before() {
        random = new Random();
    }

    @Test
    void of_illegal() {
        assertThrows(IllegalArgumentException.class, () -> Age.of(MAX_AGE_INT + 1));
        assertThrows(IllegalArgumentException.class, () -> Age.of(MIN_AGE_INT - 1));
    }

    @Test
    void less_true() {
        int randomInt = random.nextInt(MAX_AGE_INT - 1);
        Age age1 = Age.of(randomInt);
        Age age2 = Age.of(randomInt + 1);
        assertTrue(age1.less(age2));
    }

    @Test
    void less_false() {
        int randomInt = random.nextInt(MAX_AGE_INT - 1);
        Age age1 = Age.of(randomInt + 1);
        Age age2 = Age.of(randomInt);
        assertFalse(age1.less(age2));
    }

    @Test
    void greater_true() {
        int randomInt = random.nextInt(MAX_AGE_INT - 1);
        Age age1 = Age.of(randomInt + 1);
        Age age2 = Age.of(randomInt);
        assertTrue(age1.greater(age2));
    }

    @Test
    void greater_false() {
        int randomInt = random.nextInt(MAX_AGE_INT - 1);
        Age age1 = Age.of(randomInt);
        Age age2 = Age.of(randomInt + 1);
        assertFalse(age1.greater(age2));
    }

    @Test
    void testEquals_true() {
        int randomInt = random.nextInt(MAX_AGE_INT);
        Age age1 = Age.of(randomInt);
        Age age2 = Age.of(randomInt);
        assertTrue(age1.equals(age2));
        assertTrue(age1 == age2);
    }

    @Test
    void testEquals_false() {
        int randomInt = random.nextInt(MAX_AGE_INT - 1);
        Age age1 = Age.of(randomInt);
        Age age2 = Age.of(randomInt + 1);
        assertFalse(age1.equals(age2));
        assertTrue(age1 != age2);
    }

    @Test
    void intValue() {
        int randomInt = random.nextInt(MAX_AGE_INT);
        Age age = Age.of(randomInt);
        assertTrue(age.intValue() == randomInt);
    }
}