package train;

import java.time.LocalDate;

public class Carriage {

    private static long idCounter = 0;
    private long id;
    private byte doorCount;
    private short productionYear;
    private int seatCapacity;
    private int standingCapacity;
    private CarriageStatus carriageStatus;

    public Carriage(byte doorCount, short productionYear, int seats) {
        this.id = ++idCounter;
        setDoorCount(doorCount);
        setProductionYear(productionYear);
        setSeatCapacity(seats);
        setCarriageStatus(CarriageStatus.ACTIVE);
    }

    public Carriage(byte doorCount, short productionYear,
                    int seats, CarriageStatus carriageStatus) {
        this.id = ++idCounter;
        setDoorCount(doorCount);
        setProductionYear(productionYear);
        setSeatCapacity(seats);
        setCarriageStatus(carriageStatus);
    }

    public long getId() {
        return id;
    }

    public byte getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(byte doorCount) {
        if (doorCount <= 0)
            throw new IllegalArgumentException("Door count can't be negative.");
        this.doorCount = doorCount;
    }

    public short getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(short productionYear) {
        int currentYear = LocalDate.now().getYear();
        if (productionYear <= 2005 || productionYear > currentYear)
            throw new IllegalArgumentException("Carriage production year can't be " +
                    "less then 2005 and more then current year" + currentYear);

        this.productionYear = productionYear;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seats) {
        if (seats < 0){
            throw new IllegalArgumentException("Seat's capacity can't be negative.");
        }
        this.seatCapacity = seats;
    }

    public int getStandingCapacity() {
        return standingCapacity;
    }

    public void setStandingCapacity(int standingCapacity) {
        if (seatCapacity < 0){
            throw new IllegalArgumentException("Standing capacity can't be negative.");
        }
        this.standingCapacity = standingCapacity;
    }

    public CarriageStatus getCarriageStatus() {
        return carriageStatus;
    }

    public void setCarriageStatus(CarriageStatus carriageStatus) {
        if (carriageStatus == null){
            throw new IllegalArgumentException("Carriage status can't be null.");
        }
        this.carriageStatus = carriageStatus;
    }

    public boolean isCarriageActive(){
        return CarriageStatus.ACTIVE.equals(carriageStatus);
    }

    public int getCarriageTotalCapacity(){
        return seatCapacity + standingCapacity;
    }
}
