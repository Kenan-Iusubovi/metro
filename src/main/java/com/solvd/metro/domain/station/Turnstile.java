package com.solvd.metro.domain.station;

import com.solvd.metro.application.exception.InvalidTicketException;
import com.solvd.metro.application.exception.TurnstileUnavailableException;
import com.solvd.metro.application.port.OpenClose;
import com.solvd.metro.application.port.TicketValidator;
import com.solvd.metro.domain.people.passenger.Passenger;
import com.solvd.metro.domain.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Turnstile implements OpenClose {

    private static long idCounter = 0;
    private static final Logger logger = LogManager.getLogger(Turnstile.class);

    private Long id;
    private String code;
    private boolean active;
    private boolean closed;

    public Turnstile(String code, boolean active) {
        this.id = ++idCounter;
        setCode(code);
        setActive(active);
        close();
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.isBlank()) {
            logger.error("Code can't be null or negative.");
            throw new IllegalArgumentException("Code can't be null or negative.");
        }
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void open() {
        if (!active) {
            logger.error("Cannot open deactivated turnstile {}.", code);
            throw new TurnstileUnavailableException("Cannot open" +
                    " deactivated turnstile " + code + ".");
        }
        this.closed = false;
        System.out.printf("Turnstile with code%s opened.%n", code);
    }

    @Override
    public void close() {
        this.closed = true;
    }

    public void pass(Passenger passenger, Ticket ticket, TicketValidator ticketValidator) {
        if (!active) {
            logger.error("Turnstile {} is deactivated.", code);
            throw new TurnstileUnavailableException("Turnstile " + code + " is deactivated.");
        }

        if (!ticketValidator.validate(passenger, ticket)) {
            logger.error("Not valid ticket presented.");
            throw new InvalidTicketException("Not valid ticket presented.");
        }
        if (ticket.useForEntry()) {
           logger.info("Ticket {} accepted at turnstile {}%n", ticket.getCode(), code);
            open();
            logger.info("Passenger goes throw.%n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info("Passenger goes inside the station.%n");
            close();
           logger.info("Turnstile {} is closed!%n", code);
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Turnstile turnstile)) return false;
        return Objects.equals(id, turnstile.id) && Objects.equals(code, turnstile.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}
