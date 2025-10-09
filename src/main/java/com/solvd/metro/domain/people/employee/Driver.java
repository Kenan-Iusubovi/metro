package com.solvd.metro.domain.people.employee;



import com.solvd.metro.domain.train.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Objects;

public class Driver extends Employee {

    private static final Logger logger = LogManager.getLogger(Driver.class);

    private Train assignedTrain;

    public Driver(String firstname, String surname, String licenseNumber, int yearsOfExperience) {
        super(firstname, surname, licenseNumber, yearsOfExperience);
    }

    public Driver(String firstname, String surname, LocalDate dateOfBirth,
                  String licenseNumber, int yearsOfExperience) {
        super(firstname, surname, dateOfBirth, licenseNumber, yearsOfExperience);
    }

    public Driver(String firstname, String surname, String licenseNumber,
                  int yearsOfExperience, Train train) {
        super(firstname, surname, licenseNumber, yearsOfExperience);
        assignTrain(train);
    }

    public Driver(String firstname, String surname, LocalDate dateOfBirth,
                  String licenseNumber, int yearsOfExperience, Train train) {
        super(firstname, surname, dateOfBirth, licenseNumber, yearsOfExperience);
        assignTrain(train);
    }

    public Train getAssignedTrain() {
        return assignedTrain;
    }

    public void assignTrain(Train train) {
        if (train == null) {
            logger.error("Train you want to assign to the driver can't be null.");
            throw new IllegalArgumentException("Train you want to assign to the driver can't be null.");
        }
        if (this.assignedTrain != null && isWorking()) {
            logger.info(
                    "Driver {} {} (License: {}) is already driving train with code {}" +
                            "Please stop working to assign a new train.%n",
                    getFirstname(), getSurname(), getLicenseNumber(), this.assignedTrain.getCode()
            );
            return;
        }
        this.assignedTrain = train;
        logger.info("Driver {} {} (License: {}) was assigned to train with code {}.%n",
                getFirstname(), getSurname(), getLicenseNumber(), train.getCode());
    }

    @Override
    public void startWorking() {
        if (isWorking()) {
            logger.info("Driver {} {} is already on duty driving train {}.%n",
                    getFirstname(), getSurname(),
                    (assignedTrain != null ? assignedTrain.getCode() : "<none>"));
            return;
        }
        if (assignedTrain == null) {
            logger.error("No train assigned to driver.");
            throw new IllegalStateException("No train assigned to driver.");
        }
        setWorking(true);
        logger.info(
                "Driver {} {}} (License: {}) started the shift and is now driving train {}.%n",
                getFirstname(), getSurname(), getLicenseNumber(), assignedTrain.getCode()
        );
    }

    @Override
    public void stopWorking() {
        if (!isWorking()) {
            logger.info("Driver {} {} is not currently working.%n",
                    getFirstname(), getSurname());
            return;
        }
        logger.info(
                "Driver {} {} (License: {}) finished the shift and stopped driving train {}.%n",
                getFirstname(), getSurname(), getLicenseNumber(),
                (assignedTrain != null ? assignedTrain.getCode() : "<none>")
        );

        setWorking(false);
        this.assignedTrain = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Driver driver = (Driver) o;
        return Objects.equals(assignedTrain, driver.assignedTrain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), assignedTrain);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "licenseNumber='" + getLicenseNumber() + '\'' +
                ", yearsOfExperience=" + getYearsOfExperience() +
                ", working=" + isWorking() +
                ", firstname='" + getFirstname() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                '}';
    }
}
