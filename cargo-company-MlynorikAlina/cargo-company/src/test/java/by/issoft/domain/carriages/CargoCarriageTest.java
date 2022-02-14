package by.issoft.domain.carriages;

import by.issoft.domain.Weight;
import by.issoft.domain.cargo.Cargo;
import by.issoft.domain.cargo.data.CargoDataTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CargoCarriageTest {
    private CargoDataTest cargoData = new CargoDataTest();
    private static final Logger logger = LoggerFactory.getLogger(CargoCarriageTest.class);

    @Test
    void addCargo_null() {
        logger.info("--------------------------------\n\n" + CargoCarriageTest.class + " addCargo_null");
        CargoCarriage carriage = new CargoCarriage(Weight.of(10));
        assertThrows(NullPointerException.class, () -> carriage.addCargo((Cargo) null));
    }

    @Test
    void addCargo_heavierThanCapacity() {
        logger.info("--------------------------------\n\n" + CargoCarriageTest.class + " addCargo_heavierThanCapacity");
        CargoCarriage carriage = new CargoCarriage(Weight.of(10));
        assertThrows(IllegalStateException.class, () -> carriage.addCargo(cargoData.heavyCaro()));
    }

    @Test
    void addCargo_success() {
        logger.info("--------------------------------\n\n" + CargoCarriageTest.class + " addCargo_success");
        CargoCarriage carriage = new CargoCarriage(Weight.of(10));
        Cargo lightCargo = cargoData.lightCaro();
        assertTrue(carriage.addCargo(lightCargo));
        assertEquals(lightCargo, carriage.getCargoList().get(0));
    }

    @Test
    void addCargoList_hasNullCargo() {
        logger.info("--------------------------------\n\n" + CargoCarriageTest.class + " addCargoList_hasNullCargo");
        CargoCarriage carriage = new CargoCarriage(Weight.of(10));
        List<Cargo> cargoList = new ArrayList<>();
        cargoList.add((Cargo) null);
        assertThrows(NullPointerException.class, () -> carriage.addCargo(cargoList));
    }

    @Test
    void addCargoList_heavierThanCapacity() {
        logger.info("--------------------------------\n\n" + CargoCarriageTest.class + " addCargoList_heavierThanCapacity");
        CargoCarriage carriage = new CargoCarriage(Weight.of(10));
        List<Cargo> cargoList = List.of(cargoData.lightCaro(), cargoData.lightCaro(), cargoData.heavyCaro());
        assertThrows(IllegalStateException.class, () -> carriage.addCargo(cargoList));
    }

    @Test
    void addCargoList_success() {
        logger.info("--------------------------------\n\n" + CargoCarriageTest.class + " addCargoList_success");
        CargoCarriage carriage = new CargoCarriage(Weight.of(10));
        List<Cargo> cargoList = List.of(cargoData.lightCaro(), cargoData.lightCaro(), cargoData.lightCaro());
        assertTrue(carriage.addCargo(cargoList));
        assertEquals(cargoList, carriage.getCargoList());
    }

}