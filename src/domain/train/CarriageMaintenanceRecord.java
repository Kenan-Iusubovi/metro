package domain.train;

import application.exception.NoEmployeeAssignedException;
import domain.people.employee.Mechanic;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CarriageMaintenanceRecord {

    private UUID id;
    private Carriage[] carriage;
    private LocalDateTime maintenanceStartTime;
    private LocalDateTime maintenanceEndTime;
    private String description;
    private Mechanic mechanic;

    public CarriageMaintenanceRecord(Carriage[] carriage, Mechanic mechanic) {
        this.id = UUID.randomUUID();
        setCarriage(carriage);
        this.maintenanceStartTime = LocalDateTime.now();
        setMechanic(mechanic);
    }

    public CarriageMaintenanceRecord(Carriage[] carriage) {
        this.id = UUID.randomUUID();
        setCarriage(carriage);
        this.maintenanceStartTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Carriage[] getCarriage() {
        return carriage;
    }

    private void setCarriage(Carriage[] carriage) {
        if (carriage == null || carriage.length == 0) {
            throw new IllegalArgumentException("Carriage on repair can't be null or empty.");
        }
        this.carriage = carriage;
    }

    public LocalDateTime getMaintenanceStartTime() {
        return maintenanceStartTime;
    }

    public LocalDateTime getMaintenanceEndTime() {
        return maintenanceEndTime;
    }

    public void openTicket(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cant be null or empty.");
        }
        if (mechanic == null) {
            throw new NoEmployeeAssignedException("No mechanic assigned for repair number "
                    + this.id);
        }
        this.maintenanceStartTime = LocalDateTime.now();
        this.description = description;
        System.out.println("Maintenance number + " + this.id +
                " started at  " + maintenanceStartTime +
                " with description + " + description);
    }

    public void closeTicket() {
        this.maintenanceEndTime = LocalDateTime.now();
        System.out.printf(
                "Maintenance number %s finished at %s application.service provided by %s %s with" +
                        " license number %s.%n",
                this.id, maintenanceEndTime,
                mechanic.getFirstname(), mechanic.getSurname(), mechanic.getLicenseNumber());
    }

    public String getDescription() {
        return description;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        if (mechanic == null) {
            throw new IllegalArgumentException("Mechanic assigned to " +
                    "the maintenance record can't be null.");
        }
        this.mechanic = mechanic;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CarriageMaintenanceRecord that = (CarriageMaintenanceRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(carriage, that.carriage)
                && Objects.equals(maintenanceStartTime, that.maintenanceStartTime)
                && Objects.equals(maintenanceEndTime, that.maintenanceEndTime)
                && Objects.equals(description, that.description)
                && Objects.equals(mechanic, that.mechanic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carriage, maintenanceStartTime,
                maintenanceEndTime, description, mechanic);
    }

    @Override
    public String toString() {
        return "CarriageMaintenanceRecord{" +
                "id=" + id +
                ", carriage=" + Arrays.toString(carriage) +
                ", maintenanceStartTime=" + maintenanceStartTime +
                ", maintenanceEndTime=" + maintenanceEndTime +
                ", description='" + description + '\'' +
                ", mechanic=" + mechanic +
                '}';
    }
}
