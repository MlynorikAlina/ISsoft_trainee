package by.issoft.domain.cargo.data;

import by.issoft.domain.Weight;
import by.issoft.domain.cargo.Cargo;

import java.util.Random;
import java.util.UUID;

public class CargoDataTest {
    public static Random random = new Random();

    public Cargo cargoWithNullWeight() {
        return new Cargo(null, UUID.randomUUID().toString());
    }

    public Cargo cargoWithNullAddress() {
        return new Cargo(Weight.of(random.nextInt(Integer.MAX_VALUE)), null);
    }

    public Cargo cargoWithEmptyAddress() {
        return new Cargo(Weight.of(random.nextInt(Integer.MAX_VALUE)), "");
    }

    public Cargo someCargo() {
        return new Cargo(Weight.of(random.nextInt(Integer.MAX_VALUE)), UUID.randomUUID().toString());
    }

    public Cargo heavyCaro() {
        return new Cargo(Weight.of(Integer.MAX_VALUE), UUID.randomUUID().toString());
    }

    public Cargo lightCaro() {
        return new Cargo(Weight.of(1), UUID.randomUUID().toString());
    }
}
