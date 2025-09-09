package human;

import java.time.LocalDate;

public abstract class Human {

    private String firstname;
    private String surname;
    private LocalDate dateOfBirth;


    public Human(String firstname, String surname) {
        setFirstname(firstname);
        setSurname(surname);
    }

    public Human(String firstname, String surname, LocalDate dateOfBirth) {
        setFirstname(firstname);
        setSurname(surname);
        setDateOfBirth(dateOfBirth);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("Firstname can't be null or empty.");
        }
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty()) {
            throw new IllegalArgumentException("Surname can't be null or empty.");
        }
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null){
            throw new IllegalArgumentException("Date of birth can't be null.");
        }
        this.dateOfBirth = dateOfBirth;
    }
}
