package by.issoft.domain.carriages;

import by.issoft.domain.Ticket;
import by.issoft.domain.users.Passenger;
import by.issoft.storage.TicketStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.inject.internal.util.Preconditions.checkNotNull;

public class PassengerCarriage extends Carriage {
    private static final Logger logger = LoggerFactory.getLogger(CargoCarriage.class);
    private final int seatsNumber;
    private final TicketStorage ticketPool;
    private final Set<Passenger> passengers;

    public PassengerCarriage(int seatsNumber, TicketStorage ticketPool, List<Passenger> passengers) {
        logger.debug("creating PassengerCarriage " + this.toString());
        this.seatsNumber = seatsNumber;
        checkNotNull(ticketPool, "ticketPool cant be null");
        checkNotNull(passengers, "list of passengers should contain something");
        this.ticketPool = ticketPool;
        passengers.forEach(Passenger::validate);
        passengers.forEach(passenger -> this.hasSeatForTicket(passenger.getTicket()));
        this.passengers = new HashSet<>(passengers);
    }

    public PassengerCarriage(int seatsNumber, TicketStorage ticketPool) {
        logger.debug("creating PassengerCarriage " + this.toString());
        checkNotNull(ticketPool, "ticketPool cant be null");
        this.seatsNumber = seatsNumber;
        this.ticketPool = ticketPool;
        this.passengers = new HashSet<>();
    }

    public boolean addPassenger(Passenger passenger) {
        logger.debug("adding passenger " + passenger.getFullName() + " to carriage " + this.toString());
        passenger.validate();
        this.hasSeatForTicket(passenger.getTicket());
        return passengers.add(passenger);
    }

    public boolean addPassenger(List<Passenger> passengers) {
        passengers.forEach(Passenger::validate);
        passengers.forEach(passenger -> this.hasSeatForTicket(passenger.getTicket()));
        return this.passengers.addAll(passengers);
    }

    private boolean hasSeatForTicket(Ticket ticket) {
        if (ticketPool.hasTicket(ticket)) {
            logger.debug("there is seat matches ticket");
            return true;
        } else {
            logger.debug("there is no seat matches ticket, cant place the passenger");
            throw new IllegalStateException("there is no seat matches your ticket in this carriage");
        }
    }
}
