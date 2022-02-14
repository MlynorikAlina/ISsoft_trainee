package by.issoft.domain.users;

import by.issoft.domain.Age;
import by.issoft.domain.License;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DriverTest {

    @Test
    void check_createYoungDriver() {
        assertThrows(IllegalArgumentException.class, () -> new Driver("vasya", "pupkin", Age.of(16)));
    }

    @Test
    void check_createLegalDriver() {
        Driver legalDriver = new Driver("petya", "pupkin", Age.of(18));
        assertEquals(legalDriver.getLicense(), License.getLicense(legalDriver));
    }
}