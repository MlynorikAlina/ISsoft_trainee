package by.issoft.domain.cargo;

import by.issoft.domain.Weight;

import java.util.Objects;
import java.util.UUID;

import static com.google.inject.internal.util.Preconditions.checkArgument;
import static com.google.inject.internal.util.Preconditions.checkNotNull;

public class Cargo {
    private Weight weight;
    private final UUID id;
    private String shippingAddress;
    private CargoType type;
    private String notes;

    public Cargo(Weight weight, String shippingAddress) {
        this.weight = weight;
        this.shippingAddress = shippingAddress;
        id = UUID.randomUUID();
        type = CargoType.UNDEFINED;
    }

    public boolean validate() {
        checkNotNull(weight, "weight cant be null");
        checkNotNull(shippingAddress, "address cant be null");
        checkArgument(!"".equals(shippingAddress), "address should contain some text");
        return true;
    }

    public void setType(CargoType type) {
        this.type = type;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UUID getId() {
        return id;
    }

    public Weight getWeight() {
        return weight;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public CargoType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cargo cargo = (Cargo) o;
        return id.equals(cargo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
