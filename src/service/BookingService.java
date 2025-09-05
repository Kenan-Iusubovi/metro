package service;

import payment.PaymentMethod;
import people.passenger.Passenger;
import route.Line;
import route.Route;
import station.Station;
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

    public Ticket book(Metro metro, Passenger passenger,
                       long lineCode, Station origin, Station destination,
                       PaymentMethod method) {
        if (metro == null || passenger == null || origin == null ||
                destination == null || method == null) {
            throw new IllegalArgumentException("Metro, passenger, line code, station, destination," +
                    "payment method can't be null or empty.");
        }
        Line line = findLine(metro, lineCode);
        if (line == null || !line.isActive()) {
            throw new IllegalArgumentException("Line can't be null or inactive");
        }
        if (!line.hasStation(origin) || !line.hasStation(destination)) {
            throw new IllegalArgumentException("Path must be initialized and " +
                    "have at least 2 stations.");
        }
        Station[] path = new Station[]{origin, destination};
        Route route = new Route(path);
        BigDecimal price = fareCalculator.calculate(route);
        metro.ge.processPayment(price, method);
        Ticket ticket = new Ticket(lineCode, origin,
                destination, price, method);
        passenger.addTicket(ticket);
        this.issuedTickets = (Ticket[]) ArrayUtils.add(this.issuedTickets, ticket);
        emailService.sendTicketPurchaseSuccess(
                passenger.getEmail(),
                passenger.getFirstname() + " " + passenger.getSurname(),
                ticket.getId(),
                line.getName(),
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

    private Line findLine(Metro metro, Long lineCode) {
        Line[] lines = metro.getLines();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] != null && lines[i].getCode() == lineCode) {
                return lines[i];
            }
        }
        return null;
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
