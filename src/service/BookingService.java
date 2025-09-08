package service;

import payment.PaymentMethod;
import people.passenger.Passenger;
import system.Metro;
import ticket.Ticket;
import utils.ArrayUtils;

import java.math.BigDecimal;

public class BookingService {

    private Ticket[] issuedTickets;
    private EmailService emailService;
    private FareCalculator fareCalculator;

    public BookingService() {
        this.issuedTickets = new Ticket[0];
        this.emailService = new EmailService();
        this.fareCalculator = new FareCalculator();
    }

    private void checkConditions(Metro metro, Passenger passenger){
        if (metro == null){
            throw new IllegalArgumentException("Metro can't be null.");
        }
        if (metro.getPaymentService() == null){
            throw new IllegalArgumentException("Payment service can't be null.");
        }
        if (passenger == null){
            throw new IllegalArgumentException("Passenger can't be null");
        }
    }

    public Ticket book(Metro metro, Passenger passenger, PaymentMethod method) {
        checkConditions(metro,passenger);

        BigDecimal price = fareCalculator.calculateTicketPrice(passenger.getCategory());
        metro.getPaymentService().processPayment(price, method);

        Ticket ticket = new Ticket(price, method);
        passenger.addTicket(ticket);

        this.issuedTickets = (Ticket[]) ArrayUtils.add(this.issuedTickets, ticket);
        emailService.sendTicketPurchaseSuccess(
                passenger.getEmail(),
                passenger.getFirstname() + " " + passenger.getSurname(),
                ticket.getCode(),
                price.toPlainString()
        );
        return ticket;
    }

    public void cancelTicket(Passenger passenger, Ticket ticket) {
        if (ticket == null || passenger == null) {
            throw new IllegalArgumentException("Ticket or passenger can't be null or empty.");
        }
        this.issuedTickets = (Ticket[]) ArrayUtils.delete(this.issuedTickets, ticket);
        passenger.removeTicket(ticket);
    }


    public Ticket[] getIssuedTickets() {
        return issuedTickets;
    }

    public void setIssuedTickets(Ticket[] issuedTickets) {
        if (issuedTickets == null){
            throw new IllegalArgumentException("Ticket can't be null.");
        }
        this.issuedTickets = issuedTickets;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        if (emailService == null) {
            throw new IllegalArgumentException("Email service can't be null.");
        }
        this.emailService = emailService;
    }

    public FareCalculator getFareCalculator() {
        return fareCalculator;
    }

    public void setFareCalculator(FareCalculator fareCalculator) {
        if (fareCalculator == null) {
            throw new IllegalArgumentException("Fare calculator can't be null.");
        }
        this.fareCalculator = fareCalculator;
    }
}
