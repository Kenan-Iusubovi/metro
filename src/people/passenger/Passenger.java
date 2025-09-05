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

    private PassengerCategory category;

    private Ticket[] tickets;

    public Passenger(String firstname, String surname,
                     String email, String phoneNumber,
                     PassengerCategory category) {

        this.id = ++idCounter;
        setFirstname(firstname);
        setSurname(surname);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setCategory(category);
        this.tickets = new Ticket[0];
    }


    public long getId() {

        return id;
    }

    public String getFirstname() {

        return firstname;
    }

    public void setFirstname(String firstname) {

        if (firstname == null || firstname.isEmpty())
            throw new IllegalArgumentException("firstname");

        this.firstname = firstname;
    }

    public String getSurname() {

        return surname;
    }

    public void setSurname(String surname) {

        if (surname == null || surname.isEmpty())
            throw new IllegalArgumentException("surname");

        this.surname = surname;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        
        if (email == null || email.isEmpty()) 
            throw new IllegalArgumentException("email");
        
        this.email = email;
    }

    public String getPhoneNumber() {
        
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        
        if (phoneNumber == null || phoneNumber.isEmpty()) throw new IllegalArgumentException("phoneNumber");
        this.phoneNumber = phoneNumber;
    }

    public PassengerCategory getCategory() {
        return category;
    }

    public void setCategory(PassengerCategory category) {
        if (category == null) throw new IllegalArgumentException("category");
        this.category = category;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void addTicket(Ticket t) {
        this.tickets = (Ticket[]) ArrayUtils.add(this.tickets, t);
    }

    public void removeTicket(Ticket t) {
        this.tickets = (Ticket[]) ArrayUtils.delete(this.tickets, t);
    }

}
