package by.issoft.service.controllers.elevator;

import by.issoft.domain.accessory.Direction;
import by.issoft.domain.accessory.MyThread;
import by.issoft.domain.accessory.RequestState;
import by.issoft.domain.building.Floor;
import by.issoft.domain.building.elevator.Elevator;
import by.issoft.domain.building.elevator.ElevatorState;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static by.issoft.domain.building.elevator.ElevatorState.GOING_DOWN;
import static by.issoft.domain.building.elevator.ElevatorState.GOING_UP;
import static javax.xml.datatype.DatatypeConstants.*;

@Slf4j
@RequiredArgsConstructor
public class ElevatorCaller {
    @Setter
    private ElevatorController elevatorController;
    private final Map<Direction, Map<Floor, RequestState>> requests =
            Map.of(Direction.UP, new HashMap<>(), Direction.DOWN, new HashMap<>());
    private final MyThread requestProcessor = new WaitingRequestProcessor();

    public synchronized boolean callElevator(Floor floor, Direction direction) {
        boolean result = false;
        if (!requests.get(direction).containsKey(floor)) {
            // log.info("Called elevator going {} on floor {}", direction, floor.getFloorNum());

            int comparisonResult = (direction == Direction.UP) ? LESSER : GREATER;
            ElevatorState state = (direction == Direction.UP) ? GOING_UP : GOING_DOWN;

            synchronized (elevatorController) {
                Optional<Map.Entry<Elevator, Integer>> elevator = elevatorController.findElevatorToComeToFloor(floor, state, comparisonResult);

                if (elevator.isPresent()) result = true;

                elevator.ifPresentOrElse((elevatorEntry) -> {
                    log.info("To {} floor called Elevator {}\n", floor.getFloorNum(), elevatorEntry.getKey());
                    elevatorEntry.getKey().addFloorToPath(floor);
                    requests.get(direction).put(floor, RequestState.IN_PROGRESS);
                }, () -> {
                    log.info("Added to Waiting\n");
                    requests.get(direction).put(floor, RequestState.WAITING);
                });

            }
        }
        return result;
    }

    RequestState getRequestState(Direction direction, Floor floor){
        return requests.get(direction).get(floor);
    }


    public void startAll() {
        requestProcessor.start();
        log.info("Elevators working...");
        elevatorController.startAllElevators();
    }

    public void removeRequest(Floor floor, Direction direction) {
        synchronized (requests) {
            requests.get(direction).remove(floor);
        }
    }

    public void pauseAll() {
        requestProcessor.pause();
        elevatorController.pauseAllElevators();
    }

    public void continueAll() {
        requestProcessor.continueExecution();
        elevatorController.continueAllElevators();
    }

    public void finishAll() {
        requestProcessor.finish();
        elevatorController.finishAllElevators();
    }

    public void putRequest(Direction direction, Floor floor, RequestState requestState) {
        requests.get(direction).put(floor, requestState);
    }


    private class WaitingRequestProcessor extends MyThread {

        private final long SECONDS_TO_SLEEP = 3;

        @SneakyThrows
        @Override
        public void run() {
            while (needRun) {
                checkIfPausedAndWait();
                checkAndCallElevator(Direction.UP);
                checkAndCallElevator(Direction.DOWN);
                TimeUnit.SECONDS.sleep(SECONDS_TO_SLEEP);
            }
        }

        private void checkAndCallElevator(Direction direction) {
            if (requests.get(direction).containsValue(RequestState.WAITING)) {
                Optional<Map.Entry<Floor, RequestState>> any = requests.get(direction).entrySet().stream()
                        .filter(e -> e.getValue() == RequestState.WAITING)
                        .findAny();
                any.ifPresent((e) -> {
                    //log.info("Called elevator from WaitingRequestProcessor");
                    removeRequest(e.getKey(), direction);
                    callElevator(e.getKey(), direction);
                });
            }
        }
    }
}



