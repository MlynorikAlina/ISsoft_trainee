package by.issoft.storage;

import by.issoft.TestData;
import by.issoft.domain.building.Floor;
import by.issoft.domain.building.elevator.Elevator;
import by.issoft.domain.building.elevator.ElevatorState;
import by.issoft.service.controllers.elevator.ElevatorController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElevatorControllerTest {

    TestData testData = new TestData();


    @SneakyThrows
    @Test
    void toPrev_toNext() {
        Floor floor1 = testData.createFloor(0);
        Floor floor2 = testData.createFloor( 1);
        ElevatorController elevatorController = testData.getElevatorController(List.of(floor1, floor2));

        Elevator elevator = elevatorController.getElevators().get(0);

        assertEquals(floor1, elevatorController.getInitialFloor(elevator));

        elevatorController.toUpper(elevator);
        assertEquals(floor2, elevatorController.getInitialFloor(elevator));

        assertThrows(IndexOutOfBoundsException.class, ()->elevatorController.toUpper(elevator));

        elevatorController.toLower(elevator);
        assertEquals(floor1, elevatorController.getInitialFloor(elevator));

        assertThrows(IndexOutOfBoundsException.class, ()->elevatorController.toLower(elevator));
    }
}