package train;

public class Carriage {

    private static long idCounter = 0;

    private long id;

    private String type;

    private byte doorCount;

    private short productionYear;

    private int seats;

    private boolean hasAC;


    public Carriage(String type, byte doorCount,
                    short productionYear, int seats,
                    boolean hasAC) {

        this.id = ++idCounter;
        setType(type);
        setDoorCount(doorCount);
        setProductionYear(productionYear);
        setSeats(seats);
        setHasAC(hasAC);
    }

    public long getId() {

        return id;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        if (type == null || type.isBlank())
            throw new IllegalArgumentException("type");

        this.type = type;
    }

    public byte getDoorCount() {

        return doorCount;
    }

    public void setDoorCount(byte doorCount) {

        if (doorCount <= 0)
            throw new IllegalArgumentException("doorCount");

        this.doorCount = doorCount;
    }

    public short getProductionYear() {

        return productionYear;
    }

    public void setProductionYear(short productionYear) {

        if (productionYear <= 0)
            throw new IllegalArgumentException("productionYear");

        this.productionYear = productionYear;
    }

    public int getSeats() {

        return seats;
    }

    public void setSeats(int seats) {

        if (seats < 0)
            throw new IllegalArgumentException("seats");

        this.seats = seats;
    }

    public boolean isHasAC() {

        return hasAC;
    }

    public void setHasAC(boolean hasAC) {

        this.hasAC = hasAC;
    }
}
