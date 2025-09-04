package train;

public class Carriage {

    private static long idCounter = 0;

    private long id;

    private int carriageNumber;

    private int maxSeats;

    private int maxStanding;

    private int occupiedSeats;

    private int occupiedStanding;

    public Carriage(int carriageNumber, int maxSeats,
                    int maxStanding, int occupiedSeats, int occupiedStanding) {
        this.id = ++idCounter;
        this.carriageNumber = carriageNumber;
        this.maxSeats = maxSeats;
        this.maxStanding = maxStanding;
        this.occupiedSeats = occupiedSeats;
        this.occupiedStanding = occupiedStanding;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCarriageNumber() {
        return carriageNumber;
    }

    public void setCarriageNumber(int carriageNumber) {
        this.carriageNumber = carriageNumber;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getMaxStanding() {
        return maxStanding;
    }

    public void setMaxStanding(int maxStanding) {
        this.maxStanding = maxStanding;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public int getOccupiedStanding() {
        return occupiedStanding;
    }

    public void setOccupiedStanding(int occupiedStanding) {
        this.occupiedStanding = occupiedStanding;
    }
}
