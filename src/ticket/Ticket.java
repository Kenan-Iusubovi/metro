package ticket;

import people.passenger.Passenger;
import route.Route;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {

    private UUID ticketNumber;

    private Route route;

    private double price;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    private TicketStatusE ticketStatus;

    private Passenger passenger;
}
