package by.issoft.service.generator;

import by.issoft.domain.Person;
import by.issoft.domain.accessory.Intensity;
import by.issoft.domain.accessory.MyThread;
import by.issoft.domain.accessory.Weight;
import by.issoft.domain.building.Floor;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class PeopleGenerator extends MyThread implements Runnable{
    private final List<Floor> floors;
    @NonNull
    private final Intensity intensity;
    @NonNull
    private final Floor floor;
    @NonNull
    private final Floor maxFloor;
    private final Random random = new Random();
    @Getter
    private final List<Person> peopleGenerated = new ArrayList<Person>();
    private static final int TIME_INTERVAL = 1;

    public PeopleGenerator(List<Floor> floors, @NonNull Intensity intensity, @NonNull Floor floor) {
        this.floors = floors;
        this.intensity = intensity;
        this.floor = floor;
        maxFloor = floors.get(floors.size() - 1);
    }

    public List<Person> generate() {
        return IntStream.range(0, intensity.intValue())
                .map(el -> random.nextInt(maxFloor.getFloorNum()))
                .filter(finalFloorNum -> finalFloorNum != floor.getFloorNum())
                .mapToObj(this::generatePersonByFloorNum)
                .toList();
    }

    public void addToQueue(Person person){
        floor.addToQueue(person);
    }

    public void callElevatorForPerson(Person person){
        floor.checkQueueAndCallElevator();
    }

    private Person generatePersonByFloorNum(int floorNum) {
        Weight randWeight = Weight.of(random.nextInt(Person.MAX_WEIGHT.intValue() - Person.MIN_WEIGHT.intValue()) + Person.MIN_WEIGHT.intValue());
        return new Person(randWeight, floors.get(floorNum));
    }

    @SneakyThrows
    @Override
    public void run() {
        while (needRun){
            checkIfPausedAndWait();
            List<Person> generated = generate();
            generated.forEach(this::addToQueue);
            generated.forEach(this::callElevatorForPerson);
            peopleGenerated.addAll(generated);
            TimeUnit.SECONDS.sleep(TIME_INTERVAL);
        }
    }
}

