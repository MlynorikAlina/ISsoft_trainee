import by.issoft.domain.accessory.Weight;
import by.issoft.domain.accessory.Intensity;
import by.issoft.domain.building.Building;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Runner {
    private static final Random rand = new Random();
    private static final int MAX_SPEED = 1500;
    private static final int MAX_DOOR_SPEED = 500;
    private static final int MAX_CAPACITY = 1500;
    private static final int MAX_INTENCITY = 100;
    private static final int MIN_SPEED = 500;
    private static final int MIN_DOOR_SPEED = 10;
    private static final int MIN_CAPACITY = 100;
    private static final int MIN_INTENCITY = 0;

    @SneakyThrows
    public static void main(String[] args) {
        int floorsNumber = 5;
        int elevatorsNumber = 2;
        List<Integer> speed = IntStream.range(0, elevatorsNumber)
                .map(i -> rand.nextInt(MAX_SPEED) + MIN_SPEED)
                .boxed().toList();
        List<Integer> doorsSpeed = IntStream.range(0, elevatorsNumber)
                .map(i -> rand.nextInt(MAX_DOOR_SPEED) + MIN_DOOR_SPEED)
                .boxed().toList();
        List<Weight> capacity = IntStream.range(0, elevatorsNumber)
                .map(i -> rand.nextInt(MAX_CAPACITY) + MIN_CAPACITY)
                .mapToObj(Weight::of).toList();
        List<Intensity> intensities = IntStream.range(0, floorsNumber)
                .map(i -> rand.nextInt(MAX_INTENCITY) + MIN_INTENCITY)
                .mapToObj(Intensity::of).toList();

        Building building = new Building(floorsNumber,
                elevatorsNumber, speed, doorsSpeed, capacity, intensities);

        Thread buildingThread = new Thread(() -> {
            try {
                building.startWorking();
                TimeUnit.SECONDS.sleep(60);
                building.finish();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        buildingThread.setDaemon(true);
        buildingThread.start();
        buildingThread.join();
        building.getStatistics();
    }
}
