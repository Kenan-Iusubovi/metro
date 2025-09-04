package people.passenger;

import ticket.Ticket;
import utils.ArrayUtils;

public class Passenger {

    private static long idCounter = 0;

    private long id;

    private String firstname;

    private String surname;

    private String email;

    private String phoneNumber;

    private PassengerCategoryE category;

    private Ticket[] tickets;

    public Passenger(String firstname, String surname,
                     String email, String phoneNumber,
                     PassengerCategoryE category) {

        this.id = ++idCounter;
        setFirstname(firstname);
        setSurname(surname);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setCategory(category);
        this.tickets = new Ticket[0];
    }

    public void addTicket(Ticket t) {

        Object[] tmp = ArrayUtils.add(this.tickets, t);
        Ticket[] out = new Ticket[tmp.length];
        for (int i = 0; i < tmp.length; i++) out[i] = (Ticket) tmp[i];
        this.tickets = out;
    }

    public void removeTicket(Ticket t) {

        Object[] tmp = ArrayUtils.delete(this.tickets, t);
        Ticket[] out = new Ticket[tmp.length];
        for (int i = 0; i < tmp.length; i++) out[i] = (Ticket) tmp[i];
        this.tickets = out;
    }

    public long getId() {
        return id;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname == null || firstname.trim().isEmpty()) throw new IllegalArgumentException("firstname");
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.trim().isEmpty()) throw new IllegalArgumentException("surname");
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("email");
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) throw new IllegalArgumentException("phoneNumber");
        this.phoneNumber = phoneNumber;
    }

    public PassengerCategoryE getCategory() {
        return category;
    }

    public void setCategory(PassengerCategoryE category) {
        if (category == null) throw new IllegalArgumentException("category");
        this.category = category;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void setTickets(Ticket[] tickets) {
        if (tickets == null) throw new IllegalArgumentException("tickets");
        this.tickets = tickets;
    }
}
