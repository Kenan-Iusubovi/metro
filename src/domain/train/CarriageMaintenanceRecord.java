package domain.train;

import application.exception.NoEmployeeAssignedException;
import domain.people.employee.Mechanic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CarriageMaintenanceRecord {

    private UUID id;
    private List<Carriage> carriages;
    private LocalDateTime maintenanceStartTime;
    private LocalDateTime maintenanceEndTime;
    private String description;
    private Mechanic mechanic;

    public CarriageMaintenanceRecord(List<Carriage> carriages, Mechanic mechanic) {
        this.id = UUID.randomUUID();
        setCarriages(carriages);
        this.maintenanceStartTime = LocalDateTime.now();
        setMechanic(mechanic);
    }

    public CarriageMaintenanceRecord(List<Carriage> carriages) {
        this.id = UUID.randomUUID();
        setCarriages(carriages);
        this.maintenanceStartTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public List<Carriage> getCarriages() {
        return carriages;
    }

    private void setCarriages(List<Carriage> carriages) {
        if (carriages == null || carriages.isEmpty()) {
            throw new IllegalArgumentException("Carriage on repair can't be null or empty.");
        }
        this.carriages = carriages;
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

        carriages.stream()
                .forEach(carriage -> {
                    carriage.setCarriageStatus(CarriageStatus.MAINTENANCE);
                    carriage.setSeverityScore(carriage.getCarriageTotalCapacity() - 0.2f);
                });


    }

    public void closeTicket() {
        this.maintenanceEndTime = LocalDateTime.now();
        System.out.printf(
                "Maintenance number %s finished at %s application.service provided by %s %s with" +
                        " license number %s.%n",
                this.id, maintenanceEndTime,
                mechanic.getFirstname(), mechanic.getSurname(), mechanic.getLicenseNumber());
        carriages.stream()
                .forEach(carriage -> carriage.setCarriageStatus(CarriageStatus.ACTIVE));
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
        return Objects.equals(id, that.id) && Objects.equals(carriages, that.carriages)
                && Objects.equals(maintenanceStartTime, that.maintenanceStartTime)
                && Objects.equals(maintenanceEndTime, that.maintenanceEndTime)
                && Objects.equals(description, that.description)
                && Objects.equals(mechanic, that.mechanic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carriages, maintenanceStartTime,
                maintenanceEndTime, description, mechanic);
    }

    @Override
    public String toString() {
        return "CarriageMaintenanceRecord{" +
                "id=" + id +
                ", carriages=" + carriages +
                ", maintenanceStartTime=" + maintenanceStartTime +
                ", maintenanceEndTime=" + maintenanceEndTime +
                ", description='" + description + '\'' +
                ", mechanic=" + mechanic +
                '}';
    }
}
