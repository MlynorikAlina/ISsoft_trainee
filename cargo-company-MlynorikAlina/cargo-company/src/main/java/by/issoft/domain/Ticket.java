package by.issoft.domain;

import static com.google.inject.internal.util.Preconditions.checkArgument;
import static com.google.inject.internal.util.Preconditions.checkNotNull;

public class Ticket {
    private String ticketNumber;

    public Ticket(String ticketNumber) {
        checkNotNull(ticketNumber, "ticketNumber should not be null");
        checkArgument(!"".equals(ticketNumber), "ticketNumber should contain some text");
        this.ticketNumber = ticketNumber;
    }

}
