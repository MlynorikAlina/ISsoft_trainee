package by.issoft.service.controllers.elevator;

import by.issoft.domain.accessory.MyThread;
import by.issoft.domain.accessory.Weight;
import by.issoft.domain.building.Floor;
import by.issoft.domain.building.elevator.Elevator;
import by.issoft.domain.building.elevator.ElevatorState;
import by.issoft.service.statistics.ElevatorStatistics;

import java.util.*;
import java.util.stream.IntStream;

import static by.issoft.domain.building.elevator.ElevatorState.*;


public class ElevatorController {
    private final List<Floor> floors;
    private final List<Elevator> elevators;
    private final Floor minFloor;
    private final Floor maxFloor;

    private final Map<Elevator, Floor> elevatorLocation = new HashMap<>();
    private final Map<Elevator, Map<Floor, Weight>> weightToRelease = new HashMap<>();
    private final Map<Elevator, Set<Floor>> floorsToVisit = new HashMap<>();

    public ElevatorController(List<Floor> floors,
                              int elevatorsNumber, ElevatorStatistics statistics,
                              List<Integer> speed, List<Integer> doorsSpeed, List<Weight> capacity) {
        this.floors = floors;
        minFloor = floors.get(0);
        maxFloor = floors.get(floors.size() - 1);

        elevators = createElevators(elevatorsNumber, this, statistics, speed, doorsSpeed, capacity);

        initElevatorLocation(elevators);
        initElevatorWeightMap(elevators);
        initFloorsToVisitSets(elevators);
    }

    private List<Elevator> createElevators(int elevatorsNumber, ElevatorController elevatorController, ElevatorStatistics statistics,
                                           List<Integer> speed, List<Integer> doorsSpeed, List<Weight> capacity) {
        ArrayList<Elevator> elevators = new ArrayList<>();
        IntStream.range(0, elevatorsNumber).forEach(i -> elevators.add(
                new Elevator(elevatorController, statistics, capacity.get(i), speed.get(i), doorsSpeed.get(i))));
        return elevators;
    }

    private void initElevatorWeightMap(List<Elevator> elevators) {
        elevators.forEach(e -> {
            weightToRelease.put(e, new HashMap<>());
            floors.forEach(f -> weightToRelease.get(e).put(f, Weight.of(0)));
        });
    }

    private void initFloorsToVisitSets(List<Elevator> elevators) {
        elevators.forEach(e -> floorsToVisit.put(e, new HashSet<>()));
    }

    private void initElevatorLocation(List<Elevator> elevators) {
        elevators.forEach(el -> elevatorLocation.put(el, minFloor));
    }

    public void toLower(Elevator elevator) {
        Floor initialFloor = getInitialFloor(elevator);
        Floor prevFloor = floors.get(initialFloor.getFloorNum() - 1);
        elevatorLocation.put(elevator, prevFloor);

        if (prevFloor == minFloor && elevator.getElevatorState() == GOING_DOWN) {
            elevator.setElevatorState(WAITING);
        }
    }

    public void toUpper(Elevator elevator) {
        Floor initialFloor = getInitialFloor(elevator);
        Floor nextFloor = floors.get(initialFloor.getFloorNum() + 1);
        elevatorLocation.put(elevator, nextFloor);

        if (nextFloor == maxFloor && elevator.getElevatorState() == GOING_UP) {
            elevator.setElevatorState(WAITING);
        }
    }

    public Floor getInitialFloor(Elevator elevator) {
        return elevatorLocation.get(elevator);
    }

    public synchronized void chooseStateFor(Elevator elevator) {
        boolean needGoingDown = floors.stream()
                .filter(f -> f.getFloorNum() < getInitialFloor(elevator).getFloorNum())
                .anyMatch(f -> containsFloorsToVisit(elevator, f));

        boolean needGoingUp = floors.stream()
                .filter(f -> f.getFloorNum() > getInitialFloor(elevator).getFloorNum())
                .anyMatch(f -> containsFloorsToVisit(elevator, f));

        if (elevator.getElevatorState() == GOING_UP && !needGoingUp || elevator.getElevatorState() == WAITING ||
                elevator.getElevatorState() == GOING_DOWN && !needGoingDown) {
            elevator.setElevatorState(WAITING);
            if (needGoingUp) elevator.setElevatorState(GOING_UP);
            if (needGoingDown) elevator.setElevatorState(GOING_DOWN);
        }
    }

    public Optional<Map.Entry<Elevator, Integer>> findElevatorToComeToFloor(Floor floor, ElevatorState state, int comparisonResult) {
        return elevatorLocation.entrySet().stream()
                .map(e -> Map.entry(e.getKey(), e.getValue().getFloorNum()))
                .filter((e) -> (Integer.compare(e.getValue(), floor.getFloorNum()) == comparisonResult
                        && e.getKey().getElevatorState() == state) || e.getKey().getElevatorState() == ElevatorState.WAITING)
                .map(e -> Map.entry(e.getKey(), Math.abs(floor.getFloorNum() - e.getValue())))
                .min(Comparator.comparingInt(Map.Entry::getValue));
    }

    public Weight getWeight(Elevator elevator, Floor floor) {
        return weightToRelease.get(elevator).get(floor);
    }

    public void putWeight(Elevator elevator, Floor floor, Weight weight) {
        weightToRelease.get(elevator).put(floor, weight);
    }

    public void addFloorsToVisit(Elevator elevator, Floor floor) {
        floorsToVisit.get(elevator).add(floor);
    }

    public void removeFloorsToVisit(Elevator elevator, Floor floor) {
        floorsToVisit.get(elevator).remove(floor);
    }

    public boolean containsFloorsToVisit(Elevator elevator, Floor floor) {
        return floorsToVisit.get(elevator).contains(floor);
    }


    public void startAllElevators() {
        elevators.forEach(Thread::start);
    }

    public void pauseAllElevators() {
        elevators.forEach(MyThread::pause);
    }

    public void continueAllElevators() {
        elevators.forEach(MyThread::continueExecution);
    }

    public void finishAllElevators() {
        elevators.forEach(MyThread::finish);
    }

    public List<Elevator> getElevators() {
        return Collections.unmodifiableList(elevators);
    }
}
