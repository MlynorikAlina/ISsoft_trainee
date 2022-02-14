package by.issoft.domain.accessory;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class IntensityTest {

    @Test
    void of_notInRange() {
        assertThrows(IllegalArgumentException.class, ()->Intensity.of(101));
        assertThrows(IllegalArgumentException.class, ()->Intensity.of(-5));
    }

    @Test
    void intValue() {
        int value = new Random().nextInt(100);
        assertEquals(value, Intensity.of(value).intValue());
        assertNotEquals(value + 1, Intensity.of(value).intValue());
    }
}