package domain.ticket;

public enum TicketStatus {
    ACTIVE("Valid for travel", true),
    USED("Already used", false),
    CANCELLED("Cancelled by user", false),
    EXPIRED("Past validity period", false),
    REFUNDED("Refund processed", false);

    private final String description;
    private final boolean validForUse;

    TicketStatus(String description, boolean validForUse) {
        this.description = description;
        this.validForUse = validForUse;
    }

    public boolean isValidForEntry() {
        return validForUse;
    }

    public String getDetailedStatus() {
        return name() + " - " + description;
    }

    public static TicketStatus fromBoolean(boolean isValid) {
        return isValid ? ACTIVE : USED;
    }
}