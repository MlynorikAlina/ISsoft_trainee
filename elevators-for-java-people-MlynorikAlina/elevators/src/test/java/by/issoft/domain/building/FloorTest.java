package by.issoft.domain.building;

import by.issoft.TestData;
import by.issoft.domain.Person;
import by.issoft.domain.accessory.Intensity;
import by.issoft.domain.accessory.Weight;
import by.issoft.service.controllers.elevator.ElevatorController;
import by.issoft.service.generator.PeopleGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static by.issoft.domain.accessory.Direction.DOWN;
import static by.issoft.domain.accessory.Direction.UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FloorTest {
    TestData testData = new TestData();

    @Test
    void pollFromQueue_sameDirection() {
        Floor floor1 = testData.createFloor(0);
        Floor floor2 = testData.createFloor(1);
        ElevatorController elevatorController = testData.getElevatorController(List.of(floor1, floor2));

        List<Person> people = IntStream.range(0, 5).mapToObj(i -> new Person(Weight.of(i * 10), floor2)).toList();
        //Weight: 0 10 20 30 40
        people.forEach(floor1::addToQueue);

        List<Person> polledExpected = people.subList(0, 2);
        List<Person> peoplePolled = floor1.pollFromQueue(elevatorController.getElevators().get(0));

        assertEquals(polledExpected, peoplePolled);
    }

    @Test
    void pollFromQueue_notSameDirection() {
        Floor floor0 = testData.createFloor(0);
        Floor floor1 = testData.createFloor(1);
        Floor floor2 = testData.createFloor(2);
        ElevatorController elevatorController = testData.getElevatorController(List.of(floor0, floor1, floor2));

        List<Person> people = List.of(new Person(Weight.of(0), floor0), new Person(Weight.of(10), floor2), new Person(Weight.of(20), floor0));
        //Weight: 0 10 20
        people.forEach(floor1::addToQueue);

        List<Person> polledExpected = List.of(people.get(0), people.get(2));
        List<Person> peoplePolled = floor1.pollFromQueue(elevatorController.getElevators().get(0));

        assertEquals(polledExpected, peoplePolled);
    }

    @Test
    void pollFromQueue_TooHeavy() {
        Floor floor0 = testData.createFloor(0);
        Floor floor1 = testData.createFloor(1);
        Floor floor2 = testData.createFloor(2);
        ElevatorController elevatorController = testData.getElevatorController(List.of(floor0, floor1, floor2));

        List<Person> people = List.of(new Person(Weight.of(30), floor0));
        people.forEach(floor1::addToQueue);

        List<Person> polledExpected = List.of();
        List<Person> peoplePolled = floor1.pollFromQueue(elevatorController.getElevators().get(0));

        assertEquals(polledExpected, peoplePolled);
    }


    @Test
    void getFloorNum() {
        int number = new Random().nextInt();
        assertEquals(number, testData.createFloor(number).getFloorNum());
    }

    @Test
    void addToQueue() {
        List<Floor> floors = IntStream.range(0, 10).mapToObj(testData::createFloor).toList();
        PeopleGenerator peopleGenerator = new PeopleGenerator(floors, Intensity.of(10), floors.get(5));

        List<Person> generated = peopleGenerator.generate();
        generated.forEach(peopleGenerator::addToQueue);

        List<Person> queueUP = generated.stream().filter(p -> p.getFinalFloor().getFloorNum() > 5).toList();
        List<Person> queueDown = generated.stream().filter(p -> p.getFinalFloor().getFloorNum() < 5).toList();

        assertEquals(queueUP, floors.get(5).getPeopleInQueue(UP));
        assertEquals(queueDown, floors.get(5).getPeopleInQueue(DOWN));
    }

}