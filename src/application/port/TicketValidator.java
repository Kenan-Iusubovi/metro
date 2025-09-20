package application.port;

import domain.people.passenger.Passenger;
import domain.ticket.Ticket;

@FunctionalInterface
public interface TicketValidator {

    boolean validate(Ticket ticket, Passenger passenger);
}
