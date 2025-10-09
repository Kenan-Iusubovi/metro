package com.solvd.metro.domain.people;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public abstract class Human {

    private static final Logger logger = LogManager.getLogger(Human.class);

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
            logger.error("Firstname can't be null or empty.");
            throw new IllegalArgumentException("Firstname can't be null or empty.");
        }
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty()) {
            logger.error("Surname can't be null or empty.");
            throw new IllegalArgumentException("Surname can't be null or empty.");
        }
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            logger.error("Date of birth can't be null.");
            throw new IllegalArgumentException("Date of birth can't be null.");
        }
        this.dateOfBirth = dateOfBirth;
    }
}
