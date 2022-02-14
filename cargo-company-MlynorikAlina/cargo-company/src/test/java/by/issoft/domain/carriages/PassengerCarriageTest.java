package by.issoft.domain.carriages;

import by.issoft.domain.Age;
import by.issoft.domain.Ticket;
import by.issoft.domain.users.Passenger;
import by.issoft.storage.TicketStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PassengerCarriageTest {
    private static final Logger logger = LoggerFactory.getLogger(PassengerCarriageTest.class);

    @Mock
    private TicketStorage carriageTicketStorage;

    @BeforeEach
    public void before() {
        carriageTicketStorage = mock(TicketStorage.class);
    }

    @Test
    void addPassenger_ticketMatches() {
        logger.info("--------------------------------\n\n" + PassengerCarriageTest.class + " addPassenger_ticketMatches");
        Passenger validPassenger = new Passenger("v", "p", Age.of(16), new Ticket("125"));
        when(carriageTicketStorage.hasTicket(validPassenger.getTicket())).thenReturn(true);

        PassengerCarriage passengerCarriage = new PassengerCarriage(15, carriageTicketStorage);
        assertTrue(passengerCarriage.addPassenger(validPassenger));
    }

    @Test
    void addPassenger_ticketNotMatches() {
        logger.info("--------------------------------\n\n" + PassengerCarriageTest.class + " addPassenger_ticketNotMatches");
        Passenger validPassenger = getPassenger();
        when(carriageTicketStorage.hasTicket(validPassenger.getTicket())).thenReturn(false);

        PassengerCarriage passengerCarriage = new PassengerCarriage(15, carriageTicketStorage);
        assertThrows(IllegalStateException.class, () -> passengerCarriage.addPassenger(validPassenger));
    }

    @Test
    void addPassengerList_ticketMatches() {
        logger.info("--------------------------------\n\n" + PassengerCarriageTest.class + " addPassengerList_ticketMatches");
        Passenger passengerIllegal = new Passenger("v", "p", Age.of(16), new Ticket("1"));
        List<Passenger> passengerList = List.of(getPassenger(), getPassenger(), passengerIllegal);
        when(carriageTicketStorage.hasTicket(any())).thenReturn(true);

        PassengerCarriage passengerCarriage = new PassengerCarriage(15, carriageTicketStorage);
        assertTrue(passengerCarriage.addPassenger(passengerList));
    }

    @Test
    void addPassengerList_ticketNotMatches() {
        logger.info("--------------------------------\n\n" + PassengerCarriageTest.class + " addPassengerList_ticketNotMatches");
        Passenger passengerIllegal = new Passenger("v", "p", Age.of(16), new Ticket("1"));
        List<Passenger> passengerList = List.of(getPassenger(), getPassenger(), passengerIllegal);
        when(carriageTicketStorage.hasTicket(any())).thenReturn(true);
        when(carriageTicketStorage.hasTicket(passengerIllegal.getTicket())).thenReturn(false);

        PassengerCarriage passengerCarriage = new PassengerCarriage(15, carriageTicketStorage);
        assertThrows(IllegalStateException.class, () -> passengerCarriage.addPassenger(passengerList));
    }

    public Passenger getPassenger() {
        return new Passenger("v", "p", Age.of(16), new Ticket("125"));
    }
}