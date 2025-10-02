package com.solvd.metro.application.port;

import com.solvd.metro.application.service.payment.PaymentMethod;
import com.solvd.metro.domain.people.passenger.Passenger;
import com.solvd.metro.domain.system.Metro;
import com.solvd.metro.domain.ticket.Ticket;


public interface BookingService {

    Ticket book(Metro metro, Passenger passenger, PaymentMethod method);

    void cancelTicket(Passenger passenger, Ticket ticket);
}
