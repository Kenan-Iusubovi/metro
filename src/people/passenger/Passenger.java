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

    private PassengerCategoryE passengerCategory;

    private Ticket[] tickets;

    public Passenger(String firstname, String surname, String email,
                     String phoneNumber, PassengerCategoryE passengerCategory) {

        this.id = ++idCounter;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passengerCategory = passengerCategory;
        this.tickets = (Ticket[]) ArrayUtils.create(tickets);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PassengerCategoryE getPassengerCategory() {
        return passengerCategory;
    }

    public void setPassengerCategory(PassengerCategoryE passengerCategory) {
        this.passengerCategory = passengerCategory;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

}
