package train;

import java.time.LocalDateTime;

public class TrainMaintenanceRecord {

    private static long idCounter = 0;

    private long id;

    private Train train;

    private LocalDateTime time;

    private String description;

    private boolean critical;

    private float severityScore;

    public TrainMaintenanceRecord(Train train, LocalDateTime time,
                                  String description, boolean critical,
                                  float severityScore) {

        this.id = ++idCounter;
        setTrain(train);
        setTime(time);
        setDescription(description);
        setCritical(critical);
        setSeverityScore(severityScore);
    }

    public long getId() {

        return id;
    }

    public Train getTrain() {

        return train;
    }

    public void setTrain(Train train) {

        if (train == null)
            throw new IllegalArgumentException("train");

        this.train = train;
    }

    public LocalDateTime getTime() {

        return time;
    }

    public void setTime(LocalDateTime time) {

        if (time == null)
            throw new IllegalArgumentException("time");

        this.time = time;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description");

        this.description = description;
    }

    public boolean isCritical() {

        return critical;
    }

    public void setCritical(boolean critical) {

        this.critical = critical;
    }

    public float getSeverityScore() {

        return severityScore;
    }

    public void setSeverityScore(float severityScore) {

        if (severityScore < 0f || severityScore > 10f)
            throw new IllegalArgumentException("severityScore");

        this.severityScore = severityScore;
    }

    public boolean isSevere() {

        return critical && severityScore > 6.5f;
    }

}
