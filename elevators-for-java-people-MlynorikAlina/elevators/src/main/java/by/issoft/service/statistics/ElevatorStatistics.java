package by.issoft.service.statistics;

import by.issoft.domain.building.Floor;
import by.issoft.domain.building.elevator.Elevator;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ElevatorStatistics {
    Map<Elevator, Map<Map.Entry<Floor, Floor>, AtomicInteger>> statistics = new HashMap<>();

    public void incrementPeopleNumberTransferred(Elevator elevator, Map.Entry<Floor, Floor> fromToFloor) {
        Optional.ofNullable(statistics.get(elevator))
                .ifPresentOrElse((es) -> Optional.ofNullable(es.get(fromToFloor))
                                .ifPresentOrElse(AtomicInteger::incrementAndGet,
                                        () ->  es.put(fromToFloor, new AtomicInteger(1))),
                        () -> statistics.put(elevator, new HashMap<>(Map.of(fromToFloor, new AtomicInteger(1)))));

    }

    public String getStatistics() {
        StringBuffer sb = new StringBuffer();
        statistics.forEach((key, value) -> value.entrySet().stream()
                .map(flE -> String.format("Elevator %s transferred %d people from %d to %d floor\n",
                        key, flE.getValue().intValue(), flE.getKey().getKey().getFloorNum(),
                        flE.getKey().getValue().getFloorNum()))
                .forEach(sb::append));
        return sb.toString();
    }
}
