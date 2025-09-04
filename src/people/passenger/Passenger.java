package people.passenger;

import ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Passenger {

    private static long idCounter = 0;

    private long id;

    private String firstname;

    private String surname;

    private String email;

    private String phoneNumber;

    private PassengerCategoryE passengerCategory;

    private List<Ticket> tickets;

    public Passenger(String firstname, String surname, String email,
                     String phoneNumber, PassengerCategoryE passengerCategory) {

        if (firstname == null || firstname.isBlank()){
            throw new IllegalArgumentException("Firstname can't be empty!");
        }
        if (surname == null || surname.isBlank()){
            throw new IllegalArgumentException("Surname can't be empty!");
        }
        if ((email == null || email.isBlank()) && (phoneNumber == null || phoneNumber.isBlank())){
            throw new IllegalArgumentException("Providing contact information email" +
                    " either phone number is obligated !");
        }

        this.id = ++idCounter;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passengerCategory = passengerCategory;
        this.tickets = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname == null || firstname.isBlank()){
            throw new IllegalArgumentException("Firstname can't be empty!");
        }
        this.firstname = firstname;
    }

    public String getSurname() {

        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isBlank()){
            throw new IllegalArgumentException("Surname can't be empty!");
        }
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can't be empty!");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number can't be empty !");
        }
        this.phoneNumber = phoneNumber;
    }

    public PassengerCategoryE getPassengerCategory() {
        return passengerCategory;
    }

    public void setPassengerCategory(PassengerCategoryE passengerCategory) {
        this.passengerCategory = passengerCategory;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTicket(Ticket ticket) {
        if (ticket == null){
            System.out.println("Ticket can't be empty");
        }
        this.tickets.add(ticket);
    }
}
