package train;

import java.time.LocalDateTime;
import java.util.UUID;

public class TrainMaintenanceRecord {

    private UUID id;
    private Carriage carriage;
    private LocalDateTime maintenanceStartTime;
    private LocalDateTime maintenanceEndTime;
    private String description;
    private float severityScore;

    public TrainMaintenanceRecord(Carriage carriage,                                  float severityScore) {
        this.id = UUID.randomUUID();
        setCarriage(carriage);
        this.maintenanceStartTime = LocalDateTime.now();
        setSeverityScore(severityScore);
    }

    public UUID getId() {
        return id;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        if (carriage == null){
            throw new IllegalArgumentException("Carriage on repair can't be null.");
        }
        this.carriage = carriage;
    }

    public LocalDateTime getMaintenanceStartTime() {
        return maintenanceStartTime;
    }

    public LocalDateTime getMaintenanceEndTime() {
        return maintenanceEndTime;
    }

    public void setMaintenanceEndTime() {
        this.maintenanceEndTime = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cant be null or empty.");
        }
        this.description = description;
    }

    public float getSeverityScore() {

        return severityScore;
    }

    public void setSeverityScore(float severityScore) {
        if (severityScore < 0.0 || severityScore > 10.0) {
            throw new IllegalArgumentException("Severity can be assign between 0.0 and 10.0");
        }
        this.severityScore = severityScore;
    }

}
