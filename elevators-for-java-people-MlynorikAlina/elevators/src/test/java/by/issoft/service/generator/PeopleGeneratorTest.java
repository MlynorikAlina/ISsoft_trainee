package by.issoft.service.generator;

import by.issoft.domain.Person;
import by.issoft.domain.accessory.Intensity;
import by.issoft.domain.building.Floor;
import by.issoft.service.controllers.elevator.ElevatorCaller;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PeopleGeneratorTest {

    private ElevatorCaller elevatorCaller = new ElevatorCaller();
    private static int MAX_FLOOR_NUM = 10;

    @Test
    void checkCreation(){
        List<Floor> floors = IntStream.range(0, MAX_FLOOR_NUM).mapToObj(this::createFloor).toList();

        assertThrows(NullPointerException.class, ()->new PeopleGenerator(null, Intensity.of(10), floors.get(5)));
        assertThrows(NullPointerException.class, ()->new PeopleGenerator(floors,  null, floors.get(5)));
        assertThrows(NullPointerException.class, ()->new PeopleGenerator(floors, Intensity.of(10), null));

    }

    @Test
    void generate() {
        List<Floor> floors = IntStream.range(0, MAX_FLOOR_NUM).mapToObj(this::createFloor).toList();
        PeopleGenerator peopleGenerator = new PeopleGenerator(floors, Intensity.of(10), floors.get(5));

        peopleGenerator.generate().forEach(p->{
            assertTrue(p.getWeight().greaterOrEquals(Person.MIN_WEIGHT));
            assertTrue(p.getWeight().lessOrEquals(Person.MAX_WEIGHT));
            assertTrue(p.getFinalFloor().getFloorNum() >= 0);
            assertTrue(p.getFinalFloor().getFloorNum() <= MAX_FLOOR_NUM - 1);
        });
    }

    Floor createFloor(int number){
        return new Floor(number, elevatorCaller);
    }
}