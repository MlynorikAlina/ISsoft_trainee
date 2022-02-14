package by.issoft.domain.building.elevator;

import by.issoft.domain.Person;
import by.issoft.domain.accessory.MyThread;
import by.issoft.domain.accessory.Weight;
import by.issoft.domain.building.Floor;
import by.issoft.service.statistics.ElevatorStatistics;
import by.issoft.service.controllers.elevator.ElevatorController;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static by.issoft.domain.building.elevator.ElevatorState.WAITING;

@Slf4j
@Getter
@NonNull
@RequiredArgsConstructor
public class Elevator extends MyThread {
    private final ElevatorController elevatorController;
    private final ElevatorStatistics statistics;
    private final Weight capacity;
    private final int speed;
    private final int doorsSpeed;
    private Weight summaryWeight = Weight.of(0);
    @Setter
    private ElevatorState elevatorState = WAITING;


    public void addFloorToPath(Floor floor) {
        elevatorController.addFloorsToVisit(this, floor);
        //log.info("ADD Elevator {} is on floor {} state {}", this, elevatorLocation.getInitialFloor(this).getFloorNum(), elevatorState);
    }

    public void updateLocation() throws InterruptedException {
        Thread.sleep(speed);
        switch (elevatorState) {
            case GOING_DOWN -> elevatorController.toLower(this);
            case GOING_UP -> elevatorController.toUpper(this);
        }

    }

    public void openDoors() throws InterruptedException {
        Thread.sleep(doorsSpeed);
        log.info("Elevator {} opened doors ", this);
    }

    public void closeDoors() throws InterruptedException {
        Thread.sleep(doorsSpeed);
        log.info("Elevator {} closed doors\n\n ", this);
        elevatorController.removeFloorsToVisit(this, elevatorController.getInitialFloor(this));
    }

    public void pollPeople() {
        Floor initialFloor = elevatorController.getInitialFloor(this);
        initialFloor.pollFromQueue(this)
                .forEach(p -> {
                    //log.info("Elevator {} polled person {} going to {} floor", this, p, p.getFinalFloor().getFloorNum());
                    placePerson(p, p.getFinalFloor());
                    statistics.incrementPeopleNumberTransferred(this, Map.entry(initialFloor, p.getFinalFloor()));
                });


        log.info("Elevator {} people in ", this);
    }

    public void checkQueueAndCallElevator(){
        Floor initialFloor = elevatorController.getInitialFloor(this);
        initialFloor.checkQueueAndCallElevator();
    }

    private void placePerson(Person p, Floor floor) {
        elevatorController.putWeight(this, floor, Weight.sum(elevatorController.getWeight(this, floor), p.getWeight()));
        summaryWeight = Weight.sum(summaryWeight, p.getWeight());
        addFloorToPath(floor);
    }

    public void freePeople() {
        Floor initialFloor = elevatorController.getInitialFloor(this);
        summaryWeight = Weight.subtract(summaryWeight, elevatorController.getWeight(this, initialFloor));
        elevatorController.putWeight(this, initialFloor, Weight.of(0));
        log.info("Elevator {} people out ", this);
    }

    private void checkPathAndChooseState() {
        elevatorController.chooseStateFor(this);
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("Elevator {} is running ", this);
        while (needRun) {
            checkIfPausedAndWait();
            checkPathAndChooseState();
            updateLocation();
            if (elevatorController.containsFloorsToVisit(this, elevatorController.getInitialFloor(this))) {
                log.info("Elevator {} is on floor {} state {}", this, elevatorController.getInitialFloor(this).getFloorNum(), elevatorState);
                openDoors();
                freePeople();
                pollPeople();
                checkQueueAndCallElevator();
                closeDoors();
            }
        }
    }
}
