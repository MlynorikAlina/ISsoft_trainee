package by.issoft.domain.building;

import by.issoft.domain.accessory.Weight;
import by.issoft.domain.accessory.Intensity;
import by.issoft.service.controllers.elevator.ElevatorCaller;
import by.issoft.service.controllers.elevator.ElevatorController;
import by.issoft.service.generator.GeneratorController;
import by.issoft.service.statistics.ElevatorStatistics;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class Building {
    private final GeneratorController generatorController;
    private final ElevatorCaller elevatorCaller;
    private final ElevatorStatistics statistics;

    public Building(int floorsNumber,
                    int elevatorsNumber, List<Integer> speed, List<Integer> doorsSpeed, List<Weight> capacity,
                    List<Intensity> intensities) {
        elevatorCaller = new ElevatorCaller();
        statistics = new ElevatorStatistics();

        List<Floor> floors = createFloors(floorsNumber);
        ElevatorController elevatorController = new ElevatorController(floors, elevatorsNumber, statistics, speed, doorsSpeed, capacity);
        elevatorCaller.setElevatorController(elevatorController);

        generatorController = new GeneratorController(floors, intensities);

        log.info("Building initialized\n");
    }

    public void startWorking() {
        elevatorCaller.startAll();
        generatorController.startAll();
    }

    public void pause() {
        elevatorCaller.pauseAll();
        generatorController.pauseAll();
    }

    public void continueExecution() {
        elevatorCaller.continueAll();
        generatorController.continueAll();
    }

    public void finish() {
        elevatorCaller.finishAll();
        generatorController.finishAll();
    }

    private List<Floor> createFloors(int floorsNumber) {
        ArrayList<Floor> floors = new ArrayList<>();
        IntStream.range(0, floorsNumber).forEach(i -> floors.add(new Floor(i, elevatorCaller)));
        return floors;
    }



    public void getStatistics(){
        log.info("\n\nStatistics:\n{}", statistics.getStatistics());
    }


}
