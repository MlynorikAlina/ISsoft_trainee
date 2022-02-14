package by.issoft.domain.building;

import by.issoft.domain.accessory.Direction;
import by.issoft.domain.building.elevator.Elevator;
import by.issoft.service.controllers.elevator.ElevatorCaller;
import by.issoft.domain.Person;
import by.issoft.storage.PeopleQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Floor {
    @Getter
    private final int floorNum;
    private final PeopleQueue peopleQueue;

    public Floor(int floorNum, ElevatorCaller elevatorCaller){
        this.floorNum = floorNum;
        peopleQueue = new PeopleQueue(elevatorCaller, this);
    }

    public void addToQueue(Person person){
        peopleQueue.add(person);
    }

    public void checkQueueAndCallElevator(){
        peopleQueue.checkQueueAndCallElevator();
    }

    public List<Person> pollFromQueue(Elevator elevator) {
        return peopleQueue.poll(elevator);
    }

    public List<Person> getPeopleInQueue(Direction direction){
        return peopleQueue.getPeopleInQueue(direction);
    }
}
