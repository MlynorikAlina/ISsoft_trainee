package by.issoft.storage;

import by.issoft.domain.Person;
import by.issoft.domain.accessory.Direction;
import by.issoft.domain.accessory.RequestState;
import by.issoft.domain.accessory.Weight;
import by.issoft.domain.building.Floor;
import by.issoft.domain.building.elevator.Elevator;
import by.issoft.service.controllers.elevator.ElevatorCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static by.issoft.domain.accessory.Direction.DOWN;
import static by.issoft.domain.accessory.Direction.UP;
import static by.issoft.domain.building.elevator.ElevatorState.GOING_DOWN;
import static by.issoft.domain.building.elevator.ElevatorState.GOING_UP;

@Slf4j
@RequiredArgsConstructor
public class PeopleQueue {
    private final ElevatorCaller elevatorCaller;
    private final Floor floor;
    private final Deque<Person> moveUp = new ArrayDeque<>();
    private final Deque<Person> moveDown = new ArrayDeque<>();

    public void add(Person person) {
        int floorNum = floor.getFloorNum();
        //log.info(person + " came to floor " + floorNum);
        if (person.getFinalFloor().getFloorNum() > floorNum) {
            moveUp.addLast(person);
        } else if (person.getFinalFloor().getFloorNum() < floorNum) {
            moveDown.addLast(person);
        }
    }

    public void checkQueueAndCallElevator() {
        checkIsNotEmptyAndCallElevator(moveUp, floor, UP);
        checkIsNotEmptyAndCallElevator(moveDown, floor, DOWN);
    }

    public synchronized List<Person> poll(Elevator elevator) {
        List<Person> polled = new ArrayList<>();
        switch (elevator.getElevatorState()) {
            case WAITING -> {
                if (moveDown.size() > moveUp.size()) {
                    elevator.setElevatorState(GOING_DOWN);
                    polled = pollPeopleFromQueue(elevator, moveDown);
                } else {
                    elevator.setElevatorState(GOING_UP);
                    polled = pollPeopleFromQueue(elevator, moveUp);
                }
            }
            case GOING_UP -> polled = pollPeopleFromQueue(elevator, moveUp);
            case GOING_DOWN -> polled = pollPeopleFromQueue(elevator, moveDown);
        }
        return polled;
    }


    private void checkIsNotEmptyAndCallElevator(Deque<Person> deque, Floor floor, Direction direction) {
        if (deque.isEmpty()) {
            elevatorCaller.removeRequest(floor, direction);
        } else {
            elevatorCaller.putRequest(direction, floor, RequestState.WAITING);
        }
    }

    private List<Person> pollPeopleFromQueue(Elevator elevator, Deque<Person> deque) {
        Weight capacity = elevator.getCapacity();
        Weight summaryWeight = elevator.getSummaryWeight();
        Deque<Person> temp = new ArrayDeque<>();
        List<Person> polled = new ArrayList<>();

        while (summaryWeight.less(capacity) && !deque.isEmpty()) {
            Person person = deque.poll();
            if (Weight.sum(person.getWeight(), summaryWeight).lessOrEquals(capacity)) {
                summaryWeight = Weight.sum(person.getWeight(), summaryWeight);
                polled.add(person);
            } else {
                temp.addFirst(person);
            }
        }
        temp.forEach(deque::addFirst);
        return polled;
    }

    public List<Person> getPeopleInQueue(Direction direction) {
        List<Person> toReturn = List.of();
        switch (direction) {
            case UP -> toReturn = moveUp.stream().toList();
            case DOWN -> toReturn = moveDown.stream().toList();
        }
        return toReturn;
    }


}
