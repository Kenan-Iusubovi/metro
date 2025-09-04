package train;

import java.time.LocalDateTime;

public class TrainMaintenanceRecord {

    private long id;

    private Train train;

    private LocalDateTime maintenanceDateTime;

    private String description;

    private double cost;

    private Mechanic mechanic;
}
