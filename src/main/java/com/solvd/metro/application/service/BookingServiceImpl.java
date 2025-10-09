package com.solvd.metro.application.service;


import com.solvd.metro.application.exception.PaymentFailedException;
import com.solvd.metro.application.port.BookingService;
import com.solvd.metro.application.port.EmailService;
import com.solvd.metro.application.service.payment.PaymentMethod;
import com.solvd.metro.application.service.payment.PaymentSession;
import com.solvd.metro.domain.people.passenger.Passenger;
import com.solvd.metro.domain.system.Metro;
import com.solvd.metro.domain.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LogManager.getLogger(BookingServiceImpl.class);

    private Set<Ticket> issuedTickets;
    private EmailService emailService;
    private FareCalculator fareCalculatorService;

    public BookingServiceImpl() {
        this.issuedTickets = new HashSet<>();
        this.emailService = new EmailServiceImpl();
        this.fareCalculatorService = new FareCalculator();
    }

    private void checkConditions(Metro metro, Passenger passenger) {
        if (metro == null) {
            logger.error("Metro can't be null.");
            throw new IllegalArgumentException("Metro can't be null.");
        }
        if (metro.getPaymentService() == null) {
            logger.error("Payment application.service can't be null.");
            throw new IllegalArgumentException("Payment application.service can't be null.");
        }
        if (passenger == null) {
            logger.error("Passenger can't be null");
            throw new IllegalArgumentException("Passenger can't be null");
        }
    }

    @Override
    public Ticket book(Metro metro, Passenger passenger, PaymentMethod method) {
        checkConditions(metro, passenger);
        BigDecimal price = fareCalculatorService.calculateTicketPrice(passenger.getCategory());

        try (PaymentSession session = new PaymentSession()) {
            session.processPayment(price, method);

            Ticket ticket = new Ticket(price, method);
            passenger.addTicket(ticket);
            this.issuedTickets.add(ticket);

            emailService.sendTicketPurchaseSuccess(
                    passenger.getEmail(),
                    passenger.getFirstname() + " " + passenger.getSurname(),
                    ticket.getCode(),
                    price.toPlainString()
            );
            return ticket;

        } catch (PaymentFailedException e) {
            logger.error("Booking failed: {} : {}", e.getMessage(), e);
            throw new RuntimeException("Booking failed: " + e.getMessage(), e);
        } finally {
            logger.info("Payment attempt finished, session cleaned up.");
            System.out.println("Payment attempt finished, session cleaned up.");
        }
    }

    @Override
    public void cancelTicket(Passenger passenger, Ticket ticket) {
        if (ticket == null || passenger == null) {
            logger.error("Ticket or passenger can't be null or empty.");
            throw new IllegalArgumentException("Ticket or passenger can't be null or empty.");
        }
        this.issuedTickets.remove(ticket);
        passenger.removeTicket(ticket);
    }


    public Set<Ticket> getIssuedTickets() {
        return issuedTickets;
    }

    private void setIssuedTickets(Set<Ticket> issuedTickets) {
        if (issuedTickets == null) {
            logger.error("Ticket can't be null.");
            throw new IllegalArgumentException("Ticket can't be null.");
        }
        this.issuedTickets = issuedTickets;
    }
}