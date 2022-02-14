package by.issoft.domain.cargo;

import by.issoft.domain.cargo.data.CargoDataTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CargoTest {
    private CargoDataTest cargoData = new CargoDataTest();

    @Test
    void validate_nullAddress() {
        assertThrows(NullPointerException.class, () -> cargoData.cargoWithNullAddress().validate());
    }

    @Test
    void validate_nullWeight() {
        assertThrows(NullPointerException.class, () -> cargoData.cargoWithNullWeight().validate());
    }

    @Test
    void validate_emptyAddress() {
        assertThrows(IllegalArgumentException.class, () -> cargoData.cargoWithEmptyAddress().validate());
    }

    @Test
    void validate_success() {
        assertTrue(cargoData.someCargo().validate());
    }
}