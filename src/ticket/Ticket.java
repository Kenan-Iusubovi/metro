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

    public Ticket(UUID ticketNumber, Route route, double price,
                  LocalDateTime validFrom, LocalDateTime validUntil,
                  TicketStatusE ticketStatus, Passenger passenger) {
        this.ticketNumber = ticketNumber;
        this.route = route;
        this.price = price;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.ticketStatus = ticketStatus;
        this.passenger = passenger;
    }


    public UUID getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(UUID ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public TicketStatusE getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatusE ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
