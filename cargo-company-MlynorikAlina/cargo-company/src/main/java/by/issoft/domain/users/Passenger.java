package by.issoft.domain.users;

import by.issoft.domain.Age;
import by.issoft.domain.Ticket;

public class Passenger extends User {
    private Ticket ticket;

    public Passenger(String firstName, String lastName, Age age, Ticket ticket) {
        super(firstName, lastName, age);
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
