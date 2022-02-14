package by.issoft;

import by.issoft.domain.accessory.Weight;
import by.issoft.domain.building.Floor;
import by.issoft.service.controllers.elevator.ElevatorCaller;
import by.issoft.service.controllers.elevator.ElevatorController;
import by.issoft.service.statistics.ElevatorStatistics;

import java.util.List;

public class TestData {
    private final ElevatorCaller elevatorCaller = new ElevatorCaller();
    public static final int CAPACITY = 20;

    public ElevatorController getElevatorController(List<Floor> floors) {
        ElevatorController elevatorController = new ElevatorController(floors, 1, new ElevatorStatistics(),
                List.of(1), List.of(1), List.of(Weight.of(CAPACITY)));
        elevatorCaller.setElevatorController(elevatorController);
        return elevatorController;
    }

    public Floor createFloor(int number){
        return new Floor(number, elevatorCaller);
    }
}
