package domain.train;

import java.util.Arrays;

public enum CarriageStatus {
    ACTIVE("Operational", true, "green"),
    MAINTENANCE("Under maintenance", false, "yellow"),
    PARKED("Parked in depot", false, "blue"),
    OUT_OF_SERVICE("Temporarily out of service", false, "red"),
    DECOMMISSIONED("Permanently removed", false, "gray");

    private final String statusDescription;
    private final boolean operational;
    private final String indicatorColor;

    CarriageStatus(String description, boolean operational, String color) {
        this.statusDescription = description;
        this.operational = operational;
        this.indicatorColor = color;
    }

    public boolean canCarryPassengers() {
        return operational && this != PARKED;
    }

    public String getStatusWithColor() {
        return statusDescription + " [" + indicatorColor + "]";
    }

    public static CarriageStatus fromString(String status) {

      return Arrays.stream(CarriageStatus.values())
                .filter(carriageStatus -> carriageStatus.name().equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No carriage status with name "
                        + status + " was found"));
    }
}