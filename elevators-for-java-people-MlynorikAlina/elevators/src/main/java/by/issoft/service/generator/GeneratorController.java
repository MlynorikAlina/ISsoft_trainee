package by.issoft.service.generator;

import by.issoft.domain.accessory.Intensity;
import by.issoft.domain.accessory.MyThread;
import by.issoft.domain.building.Floor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
public class GeneratorController {
    private final Map<Floor, PeopleGenerator> peopleGenerators = new HashMap<>();

    public GeneratorController(List<Floor> floors, List<Intensity> intensities) {
        IntStream.range(0, floors.size())
                .forEach(i->createGenerator(floors, intensities.get(i), floors.get(i)));
    }

    private void createGenerator(List<Floor> floors, Intensity intensity, Floor floor){
        PeopleGenerator pg = new PeopleGenerator(floors, intensity, floor);
        peopleGenerators.put(floor, pg);
    }

    public void startAll(){
        log.info("Starting generation...");
        peopleGenerators.values().forEach(Thread::start);
    }

    public void pauseAll() {
        peopleGenerators.values().forEach(MyThread::pause);
    }

    public void continueAll() {
        peopleGenerators.values().forEach(MyThread::continueExecution);
    }

    public void finishAll() {
        peopleGenerators.values().forEach(MyThread::finish);
    }
}
