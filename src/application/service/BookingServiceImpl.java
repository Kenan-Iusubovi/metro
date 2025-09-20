package application.service;

import application.exception.PaymentFailedException;
import application.port.BookingService;
import application.port.EmailService;
import application.service.payment.PaymentMethod;
import application.service.payment.PaymentSession;
import domain.people.passenger.Passenger;
import domain.system.Metro;
import domain.ticket.Ticket;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class BookingServiceImpl implements BookingService {

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
            throw new IllegalArgumentException("Metro can't be null.");
        }
        if (metro.getPaymentService() == null) {
            throw new IllegalArgumentException("Payment application.service can't be null.");
        }
        if (passenger == null) {
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
            throw new RuntimeException("Booking failed: " + e.getMessage(), e);
        } finally {
            System.out.println("Payment attempt finished, session cleaned up.");
        }
    }

    @Override
    public void cancelTicket(Passenger passenger, Ticket ticket) {
        if (ticket == null || passenger == null) {
            throw new IllegalArgumentException("Ticket or passenger can't be null or empty.");
        }
        this.issuedTickets.remove(ticket);
        passenger.removeTicket(ticket);
    }


    public Set<Ticket> getIssuedTickets() {
        return issuedTickets;
    }

    public void setIssuedTickets(Set<Ticket> issuedTickets) {
        if (issuedTickets == null) {
            throw new IllegalArgumentException("Ticket can't be null.");
        }
        this.issuedTickets = issuedTickets;
    }
}
