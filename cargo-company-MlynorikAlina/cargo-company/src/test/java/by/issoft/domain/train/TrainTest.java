package by.issoft.domain.train;

import by.issoft.domain.Age;
import by.issoft.domain.Ticket;
import by.issoft.domain.Weight;
import by.issoft.domain.cargo.Cargo;
import by.issoft.domain.cargo.data.CargoDataTest;
import by.issoft.domain.carriages.CargoCarriage;
import by.issoft.domain.carriages.Carriage;
import by.issoft.domain.carriages.Locomotive;
import by.issoft.domain.carriages.PassengerCarriage;
import by.issoft.domain.users.Driver;
import by.issoft.domain.users.Passenger;
import by.issoft.storage.TicketStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainTest {
    private CargoDataTest cargoData = new CargoDataTest();
    private Random random;
    private static final Logger logger = LoggerFactory.getLogger(TrainTest.class);

    @Mock
    private TicketStorage carriageTicketStorage;

    @BeforeEach
    public void before() {
        random = new Random();
        carriageTicketStorage = mock(TicketStorage.class);
    }

    @Test
    void addCarriage_null() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCarriage_null");
        Train train = new Train(UUID.randomUUID().toString());
        assertThrows(NullPointerException.class, () -> train.addCarriage((Carriage) null));
    }

    @Test
    void addCarriage_emptyTrain() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCarriage_emptyTrain");
        Train train = new Train(UUID.randomUUID().toString());
        Carriage carriage = new Locomotive(someDriver());
        train.addCarriage(carriage);
        assertEquals(carriage, train.getListOfCarriages().get(0));
    }

    @Test
    void addCarriage_notEmptyTrain() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCarriage_notEmptyTrain");
        Train train = new Train(UUID.randomUUID().toString());
        Carriage carriage1 = someLocomotive();
        Carriage carriage2 = someCargoCarriage();
        Carriage carriage3 = somePassengerCarriage();
        train.addCarriage(carriage1);
        train.addCarriage(carriage2);
        train.addCarriage(carriage3);
        List<Carriage> carriageList = train.getListOfCarriages();
        assertEquals(carriage3, carriageList.get(carriageList.size() - 1));
    }

    @Test
    void addCarriage_List() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCarriage_List");
        Train train = new Train(UUID.randomUUID().toString());
        Carriage carriage1 = someLocomotive();
        Carriage carriage2 = someCargoCarriage();
        Carriage carriage3 = somePassengerCarriage();
        train.addCarriage(List.of(carriage1, carriage2, carriage3));
        List<Carriage> carriageList = train.getListOfCarriages();
        assertEquals(carriage3, carriageList.get(carriageList.size() - 1));
    }


    @Test
    void validate_Empty() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " validate_Empty");
        Train train = new Train(UUID.randomUUID().toString());
        assertThrows(NullPointerException.class, train::validate);
    }

    @Test
    void validate_noLocomotive() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " validate_noLocomotive");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someCargoCarriage(), somePassengerCarriage()));
        assertThrows(IllegalStateException.class, train::validate);
    }

    @Test
    void validate_LocomotiveInTheMiddle() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " validate_LocomotiveInTheMiddle");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), someLocomotive(), somePassengerCarriage()));
        assertThrows(IllegalStateException.class, train::validate);
    }

    @Test
    void validate_LocomotiveInTheTail() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " validate_LocomotiveInTheTail");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someCargoCarriage(), somePassengerCarriage(), someLocomotive()));
        assertTrue(train::validate);
    }

    @Test
    void validate_LocomotiveInTheHead() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " validate_LocomotiveInTheHead");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), somePassengerCarriage(), someCargoCarriage()));
        assertTrue(train::validate);
    }

    @Test
    void addCargo_null() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCargo_null");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), somePassengerCarriage(), someCargoCarriage()));
        train.validate();
        assertThrows(NullPointerException.class, () -> train.addCargo((Cargo) null));
    }

    @Test
    void addCargo_noCargoCarriage() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCargo_noCargoCarriage");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), somePassengerCarriage(), somePassengerCarriage()));
        train.validate();
        assertThrows(IllegalStateException.class, () -> train.addCargo(cargoData.someCargo()));
    }

    @Test
    void addCargo_heavyCargo() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCargo_heavyCargo");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), somePassengerCarriage(), new CargoCarriage(Weight.of(10))));
        train.validate();
        assertThrows(IllegalStateException.class, () -> train.addCargo(cargoData.heavyCaro()));
    }

    @Test
    void addCargo_success1() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCargo_success1");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), somePassengerCarriage(), new CargoCarriage(Weight.of(10))));
        train.validate();
        assertTrue(train.addCargo(cargoData.lightCaro()));
    }

    @Test
    void addCargo_success2() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCargo_success2");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), new CargoCarriage(Weight.of(0)), new CargoCarriage(Weight.of(10))));
        train.validate();
        assertTrue(train.addCargo(cargoData.lightCaro()));
    }

    @Test
    void addCargo_ListSuccess() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCargo_ListSuccess");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), new CargoCarriage(Weight.of(0)), new CargoCarriage(Weight.of(10))));
        train.validate();
        assertTrue(train.addCargo(List.of(cargoData.lightCaro(), cargoData.lightCaro())));
    }

    @Test
    void addCargo_ListFail() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addCargo_ListFail");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), new CargoCarriage(Weight.of(0)), new CargoCarriage(Weight.of(10))));
        train.validate();
        assertThrows(IllegalStateException.class, () -> train.addCargo(List.of(cargoData.lightCaro(), cargoData.heavyCaro())));
    }

    @Test
    void addPassenger_null() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addPassenger_null");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), somePassengerCarriage(), someCargoCarriage()));
        train.validate();
        assertThrows(NullPointerException.class, () -> train.addPassenger((Passenger) null));
    }

    @Test
    void addPassenger_noPassengerCarriage() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addPassenger_noPassengerCarriage");
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), someCargoCarriage(), someCargoCarriage()));
        train.validate();
        assertThrows(IllegalStateException.class, () -> train.addPassenger(getPassenger()));
    }

    @Test
    void addPassenger_noSeatsMatchTicket() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addPassenger_noSeatsMatchTicket");
        when(carriageTicketStorage.hasTicket(any())).thenReturn(false);
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), somePassengerCarriage(), someCargoCarriage()));
        train.validate();
        assertThrows(IllegalStateException.class, () -> train.addPassenger(getPassenger()));
    }

    @Test
    void addPassenger_success() {
        logger.info("--------------------------------\n\n" + TrainTest.class + " addPassenger_success");
        when(carriageTicketStorage.hasTicket(any())).thenReturn(true);
        Train train = new Train(UUID.randomUUID().toString());
        train.addCarriage(List.of(someLocomotive(), somePassengerCarriage(), someCargoCarriage()));
        train.validate();
        assertTrue(train.addPassenger(getPassenger()));
    }

    Driver someDriver() {
        logger.info("someDriver method");
        return new Driver(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Age.of(20));
    }

    Locomotive someLocomotive() {
        logger.info("someLocomotive method");
        return new Locomotive(someDriver());
    }

    CargoCarriage someCargoCarriage() {
        logger.info("someCargoCarriage method");
        return new CargoCarriage(Weight.of(random.nextInt(Integer.MAX_VALUE)));
    }

    PassengerCarriage somePassengerCarriage() {
        logger.info("somePassengerCarriage method");
        return new PassengerCarriage(random.nextInt(Integer.MAX_VALUE), carriageTicketStorage);
    }

    Passenger getPassenger() {
        logger.info("getPassenger method");
        return new Passenger("v", "p", Age.of(16), new Ticket("125"));
    }

}