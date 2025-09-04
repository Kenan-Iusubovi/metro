package train;

import people.worker.Worker;

import java.time.LocalDateTime;

public class TrainMaintenanceRecord {

    private static long idCounter = 0;

    private long id;

    private Train train;

    private LocalDateTime maintenanceDateTime;

    private String description;

    private double cost;

    private Worker mechanic;

    public TrainMaintenanceRecord(Train train, LocalDateTime maintenanceDateTime,
                                  String description, double cost, Worker mechanic) {
        this.id = ++idCounter;
        this.train = train;
        this.maintenanceDateTime = maintenanceDateTime;
        this.description = description;
        this.cost = cost;
        this.mechanic = mechanic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalDateTime getMaintenanceDateTime() {
        return maintenanceDateTime;
    }

    public void setMaintenanceDateTime(LocalDateTime maintenanceDateTime) {
        this.maintenanceDateTime = maintenanceDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Worker getMechanic() {
        return mechanic;
    }

    public void setMechanic(Worker mechanic) {
        this.mechanic = mechanic;
    }
}
