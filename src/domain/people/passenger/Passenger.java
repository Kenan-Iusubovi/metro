package domain.people.passenger;

import domain.people.Human;
import domain.ticket.Ticket;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Passenger extends Human {

    private static long idCounter = 0;

    private Long id;
    private String email;
    private String phoneNumber;
    private PassengerCategory category;
    private Set<Ticket> tickets;

    public Passenger(String firstname, String surname, String email,
                     String phoneNumber, PassengerCategory category) {
        super(firstname, surname);
        this.id = ++idCounter;
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setCategory(category);
        this.tickets = new HashSet<>();
    }

    public Passenger(String firstname, String surname, LocalDate dateOfBirth,
                     String email, String phoneNumber, PassengerCategory category) {
        super(firstname, surname, dateOfBirth);
        this.id = ++idCounter;
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setCategory(category);
        this.tickets = new HashSet<>();
    }

    public Passenger(String firstname, String surname, LocalDate dateOfBirth,
                     String phoneNumber, PassengerCategory category) {
        super(firstname, surname, dateOfBirth);
        this.id = ++idCounter;
        setPhoneNumber(phoneNumber);
        setCategory(category);
        this.tickets = new HashSet<>();
    }

    public Passenger(String firstname, String surname, String email, PassengerCategory category) {
        super(firstname, surname);
        this.id = ++idCounter;
        setEmail(email);
        setCategory(category);
        this.tickets = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email can't be null or empty.");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number can't be empty");
        }
        this.phoneNumber = phoneNumber;
    }

    public PassengerCategory getCategory() {
        return category;
    }

    public void setCategory(PassengerCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("category can't be null.");
        }
        this.category = category;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket can't be null.");
        }
        this.tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket can't be null.");
        }
        this.tickets.add(ticket);
    }
}
