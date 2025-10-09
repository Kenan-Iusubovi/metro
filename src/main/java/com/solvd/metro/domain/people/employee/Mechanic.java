package com.solvd.metro.domain.people.employee;

import com.solvd.metro.domain.train.CarriageMaintenanceRecord;
import com.solvd.metro.domain.train.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class Mechanic extends Employee {

    private static final Logger logger = LogManager.getLogger(Mechanic.class);

    private String repairDescription;
    private Train assignedTrain;
    private CarriageMaintenanceRecord activeRecord;

    public Mechanic(String firstname, String surname, String licenseNumber, int yearsOfExperience) {
        super(firstname, surname, licenseNumber, yearsOfExperience);
        setDefaultRepairDescription();
        setWorking(false);
    }

    public Mechanic(String firstname, String surname,
                    String licenseNumber, int yearsOfExperience, Train train) {
        super(firstname, surname, licenseNumber, yearsOfExperience);
        setDefaultRepairDescription();
        setWorking(false);
        assignTrain(train);
    }

    public Mechanic(String firstname, String surname, LocalDate dateOfBirth,
                    String licenseNumber, int yearsOfExperience) {
        super(firstname, surname, dateOfBirth, licenseNumber, yearsOfExperience);
        setDefaultRepairDescription();
        setWorking(false);
    }

    public Mechanic(String firstname, String surname, LocalDate dateOfBirth,
                    String licenseNumber, int yearsOfExperience, Train train) {
        super(firstname, surname, dateOfBirth, licenseNumber, yearsOfExperience);
        setDefaultRepairDescription();
        setWorking(false);
        assignTrain(train);
    }

    @Override
    public void startWorking() {
        if (getAssignedTrain() == null) {
            logger.error("Please first assign the train which should be repaired.");
            throw new RuntimeException("Please first assign the train which should be repaired.");
        }
        if (isWorking()) {
            logger.info(
                    "Mechanic {} {} can't start a new repair, still working on the old one.%n",
                    getFirstname(), getSurname()
            );
            return;
        }
        setWorking(true);
        logger.info("Mechanic {} {} started repair.%n", getFirstname(), getSurname());

        this.activeRecord = new CarriageMaintenanceRecord(assignedTrain.getCarriages(),
                this);
        this.activeRecord.openTicket(repairDescription);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startWorking(String description) {
        if (description == null || description.isBlank()) {
            logger.error("Description of repair can't be null or empty.");
            throw new IllegalArgumentException("Description of repair can't be null or empty.");
        }
        this.repairDescription = description;
        startWorking();
    }

    @Override
    public void stopWorking() {
        if (!isWorking()) {
            logger.info("Mechanic {} {} is not currently working.%n",
                    getFirstname(), getSurname());
            return;
        }

        logger.info("Mechanic {} {} finished repair.%n", getFirstname(), getSurname());

        this.activeRecord.closeTicket();
        this.activeRecord = null;
        setDefaultRepairDescription();
        this.assignedTrain = null;
        setWorking(false);
    }

    public Train getAssignedTrain() {
        return assignedTrain;
    }

    public void assignTrain(Train train) {
        if (train == null) {
            logger.error("Train you want to assign to the Mechanic can't be null.");
            throw new IllegalArgumentException("Train you want to assign to the Mechanic can't be null.");
        }
        if (this.assignedTrain != null) {
            logger.info(
                    "Mechanic {} {} with license number {} already has an assigned train (code: {}).%n" +
                            "Please stop working to assign a new train.%n",
                    getFirstname(), getSurname(), getLicenseNumber(), this.assignedTrain.getCode()
            );
            return;
        }
        this.assignedTrain = train;
        logger.info("Mechanic {} {} was assigned to train with code {}.%n",
                getFirstname(), getSurname(), train.getCode());
    }

    private void setDefaultRepairDescription() {
        this.repairDescription = "Regular maintenance, standard system's check.";
    }
}
