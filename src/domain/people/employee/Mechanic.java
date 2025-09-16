package domain.people.employee;


import domain.train.CarriageMaintenanceRecord;
import domain.train.Train;

import java.time.LocalDate;

public class Mechanic extends Employee {

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
            throw new RuntimeException("Please first assign the train which should be repaired.");
        }
        if (isWorking()) {
            System.out.printf(
                    "Mechanic %s %s can't start a new repair, still working on the old one.%n",
                    getFirstname(), getSurname()
            );
            return;
        }
        setWorking(true);
        System.out.printf("Mechanic %s %s started repair.%n", getFirstname(), getSurname());

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
            throw new IllegalArgumentException("Description of repair can't be null or empty.");
        }
        this.repairDescription = description;
        startWorking();
    }

    @Override
    public void stopWorking() {
        if (!isWorking()) {
            System.out.printf("Mechanic %s %s is not currently working.%n",
                    getFirstname(), getSurname());
            return;
        }

        System.out.printf("Mechanic %s %s finished repair.%n", getFirstname(), getSurname());

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
            throw new IllegalArgumentException("Train you want to assign to the Mechanic can't be null.");
        }
        if (this.assignedTrain != null) {
            System.out.printf(
                    "Mechanic %s %s with license number %s already has an assigned train (code: %s).%n" +
                            "Please stop working to assign a new train.%n",
                    getFirstname(), getSurname(), getLicenseNumber(), this.assignedTrain.getCode()
            );
            return;
        }
        this.assignedTrain = train;
        System.out.printf("Mechanic %s %s was assigned to train with code %s.%n",
                getFirstname(), getSurname(), train.getCode());
    }

    private void setDefaultRepairDescription() {
        this.repairDescription = "Regular maintenance, standard system's check.";
    }
}
