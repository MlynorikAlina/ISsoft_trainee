package by.issoft.domain.building.elevator;

import by.issoft.TestData;
import by.issoft.domain.Person;
import by.issoft.domain.accessory.Weight;
import by.issoft.domain.building.Floor;
import by.issoft.service.controllers.elevator.ElevatorController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {

    TestData testData = new TestData();

    @SneakyThrows
    @Test
    void updateLocation() {
        Floor floor1 = testData.createFloor(0);
        Floor floor2 = testData.createFloor( 1);
        ElevatorController elevatorController = testData.getElevatorController(List.of(floor1, floor2));

        Elevator elevator = elevatorController.getElevators().get(0);

        assertEquals(floor1, elevatorController.getInitialFloor(elevator));

        elevator.setElevatorState(ElevatorState.GOING_UP);
        elevator.updateLocation();
        assertEquals(floor2, elevatorController.getInitialFloor(elevator));

        elevator.updateLocation();
        assertEquals(floor2, elevatorController.getInitialFloor(elevator));
        assertEquals(ElevatorState.WAITING, elevator.getElevatorState());

        elevator.setElevatorState(ElevatorState.GOING_DOWN);
        elevator.updateLocation();
        assertEquals(floor1, elevatorController.getInitialFloor(elevator));

        elevator.updateLocation();
        assertEquals(floor1, elevatorController.getInitialFloor(elevator));
        assertEquals(ElevatorState.WAITING, elevator.getElevatorState());
    }

    @Test
    void pollPeople_freePeople_sameFloor() throws InterruptedException {
        Floor floor1 = testData.createFloor(0);
        Floor floor2 = testData.createFloor(1);
        ElevatorController elevatorController = testData.getElevatorController(List.of(floor1, floor2));

        List<Person> people = IntStream.range(0, 5).mapToObj(i -> new Person(Weight.of(i * 10), floor2)).toList();
        //Weight: 0 10 20 30 40
        people.forEach(floor1::addToQueue);

        Elevator elevator = elevatorController.getElevators().get(0);
        elevator.pollPeople();

        assertEquals(Weight.of(10), elevator.getSummaryWeight());
        assertEquals(Weight.of(10), elevatorController.getWeight(elevator, floor2));

        elevator.updateLocation();
        elevator.freePeople();

        assertEquals(Weight.of(0), elevator.getSummaryWeight());
        assertEquals(Weight.of(0), elevatorController.getWeight(elevator, floor2));
    }

    @SneakyThrows
    @Test
    void pollPeople_freePeople_notSameDirection() {
        Floor floor0 = testData.createFloor(0);
        Floor floor1 = testData.createFloor(1);
        Floor floor2 = testData.createFloor(2);
        ElevatorController elevatorController = testData.getElevatorController(List.of(floor0, floor1, floor2));

        List<Person> people = List.of(new Person(Weight.of(0), floor0), new Person(Weight.of(10), floor2), new Person(Weight.of(20), floor0));
        //Weight: 0 10 20
        people.forEach(floor1::addToQueue);

        Elevator elevator = elevatorController.getElevators().get(0);

        assertEquals(Weight.of(0), elevator.getSummaryWeight());

        elevatorController.toUpper(elevator);
        elevator.setElevatorState(ElevatorState.GOING_DOWN);
        elevator.pollPeople();

        assertEquals(Weight.of(20), elevator.getSummaryWeight());
        assertEquals(Weight.of(20), elevatorController.getWeight(elevator, floor0));

        elevator.updateLocation();
        elevator.freePeople();

        assertEquals(Weight.of(0), elevator.getSummaryWeight());
        assertEquals(Weight.of(0), elevatorController.getWeight(elevator, floor0));
    }
}