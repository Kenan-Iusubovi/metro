package application.port;

import domain.system.Metro;
import domain.ticket.Ticket;
import domain.people.passenger.Passenger;
import application.service.payment.PaymentMethod;

public interface BookingService {

    Ticket book(Metro metro, Passenger passenger, PaymentMethod method);
    void cancelTicket(Passenger passenger, Ticket ticket);
}
