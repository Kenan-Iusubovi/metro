package application.port;

import application.service.payment.PaymentMethod;
import domain.people.passenger.Passenger;
import domain.system.Metro;
import domain.ticket.Ticket;

public interface BookingService {

    Ticket book(Metro metro, Passenger passenger, PaymentMethod method);

    void cancelTicket(Passenger passenger, Ticket ticket);
}
