package by.issoft.domain.carriages;

import by.issoft.domain.Weight;
import by.issoft.domain.cargo.Cargo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.inject.internal.util.Preconditions.checkNotNull;

public class CargoCarriage extends Carriage {
    private static final Logger logger = LoggerFactory.getLogger(CargoCarriage.class);
    private final Weight capacity;
    private final List<Cargo> cargoList;
    private Weight summaryCargoWeight;

    public CargoCarriage(Weight capacity) {
        logger.debug("creating CargoCarriage " + this.toString());
        checkNotNull(capacity, "capacity cant be null");
        this.capacity = capacity;
        this.cargoList = new ArrayList<>();
        summaryCargoWeight = Weight.of(0);
    }

    public List<Cargo> getCargoList() {
        return List.copyOf(cargoList);
    }

    public boolean addCargo(List<Cargo> cargoList) {
        long summaryIntWeight = summaryCargoWeight.intValue();
        for (Cargo cargo : cargoList) {
            logger.debug("adding cargo " + cargo.getId() + " to carriage " + this.toString());
            checkNotNull(cargo, "cant add null cargo");
            cargo.validate();
            summaryIntWeight += cargo.getWeight().intValue();
        }
        if (summaryIntWeight > capacity.intValue()) {
            logger.debug("cargo was not added");
            throw new IllegalStateException("summary cargo weight is greater than " + capacity);
        }
        logger.debug("cargo added");
        summaryCargoWeight = Weight.of((int) summaryIntWeight);
        this.cargoList.addAll(cargoList);
        return true;
    }

    public boolean addCargo(Cargo cargo) {
        logger.debug("adding cargo " + cargo.getId() + " to carriage " + this.toString());
        checkNotNull(cargo, "cant add null cargo");
        if ((long) summaryCargoWeight.intValue() + cargo.getWeight().intValue() > capacity.intValue()) {
            logger.debug("cargo was not added");
            throw new IllegalStateException("summary cargo weight is greater than " + capacity);
        }
        summaryCargoWeight = Weight.of(summaryCargoWeight.intValue() + cargo.getWeight().intValue());
        logger.debug("cargo added");
        cargo.validate();
        cargoList.add(cargo);
        return true;
    }
}
