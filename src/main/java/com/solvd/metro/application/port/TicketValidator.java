package com.solvd.metro.application.port;

import com.solvd.metro.domain.people.passenger.Passenger;
import com.solvd.metro.domain.ticket.Ticket;

@FunctionalInterface
public interface TicketValidator {

    boolean validate(Passenger passenger, Ticket ticket);
}
