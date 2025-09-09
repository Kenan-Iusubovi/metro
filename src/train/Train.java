package train;

import human.employee.Driver;
import human.passenger.Passenger;
import route.Line;
import station.Station;
import utils.ArrayUtils;

import java.time.LocalTime;

public class Train {

    private static long idCounter = 0;

    private long id;
    private String code;
    private Carriage[] carriages;
    private int onboardCount;
    private byte acTemperature;
    boolean isDoorsOpen;
    private Driver driver;

    public Train(Carriage[] carriages) {
        this.id = ++idCounter;
        setCarriages(carriages);
        this.onboardCount = 0;
    }

    public Train(String code, Carriage[] carriages) {
        this.id = ++idCounter;
        setCode(code);
        setCarriages(carriages);
        this.onboardCount = 0;
    }

    private boolean board(Passenger passenger) {
        openDoors();
        onboardCount++;
        if (onboardCount > getCapacity()){
            System.err.printf("The train is full%n");
            onboardCount--;
            return false;

        }
        System.out.printf("%s%s passenger just unboarded, total amount of passengers %d%n",
                passenger.getFirstname(), passenger.getSurname(), onboardCount);
        return true;
    }

    private void alight(Passenger passenger) {
        closeDoors();
        onboardCount --;
        System.out.printf("%s%salight from train%n",
                passenger.getFirstname(), passenger.getSurname());
        closeDoors();
    }

    private void openDoors(){
        isDoorsOpen = true;
        System.out.printf("%d door's opened%n", getDoorsAmount());
    }

    private void closeDoors(){
        isDoorsOpen = false;
        System.out.printf("%d door's closed%n", getDoorsAmount());
    }

    public boolean isFull() {
        return onboardCount >= getCapacity();
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code can't be null or empty.");
        }
        this.code = code;
    }

    public Carriage[] getCarriages() {

        return carriages;
    }

    public void addCarriage(Carriage carriage) {
        if (carriage == null){
            throw new IllegalArgumentException("Carriage to add can't be null.");
        }
        this.carriages = (Carriage[]) ArrayUtils.add(this.carriages, carriage);
    }

    public void removeCarriage(Carriage carriage) {
        if (carriage == null){
            throw new IllegalArgumentException("Carriage to remove can't be null.");
        }
        this.carriages = (Carriage[]) ArrayUtils.delete(this.carriages, carriage);
    }

    private void setCarriages(Carriage[] carriages){
        if (carriages == null) {
            throw new IllegalArgumentException("Carriages can't be null");
        }
        if (carriages.length < 2){
            throw new IllegalArgumentException("To make a train should add minimum 2 carriages.");
        }
        this.carriages = new Carriage[0];
        for (Carriage carriage : carriages){
            addCarriage(carriage);
        }
    }

    public Driver getDriver() {
        return driver;
    }

    public void assignDriver(Driver driver) {
        if (driver == null){
            throw new IllegalArgumentException("You can't assign null instead of driver");
        }
        this.driver = driver;
    }

    public void removeDriver(){
        this.driver = null;
    }

    public byte getAcTemperature() {
        return acTemperature;
    }

    public void setAcTemperature(byte temperature) {
        if (temperature < 16 || temperature > 32)
            throw new IllegalArgumentException("AC temperature can be set between 16 C° - 32 C°");

        this.acTemperature = temperature;
    }

    public int getCapacity(){
        int capacity = 0;
        for (Carriage c : carriages){
            capacity += c.getCarriageTotalCapacity();
        }
        return capacity;
    }

    public int getDoorsAmount(){
        int doorCount = 0;
        for (Carriage carriage : carriages){
            doorCount += carriage.getDoorCount();
        }
        return doorCount;
    }

    private Station go(Line line, Station destination, Passenger passenger, boolean continueToTerminus){
        if (line == null){
            throw new IllegalArgumentException("Nowhere to go line is null.");
        }
        if (line.getStations() == null){
            throw new IllegalArgumentException("Nowhere to go stations is null.");
        }
        if (driver == null){
            throw new RuntimeException("To be able run metro system you should assign driver for each train!");
        }

        Station[] stations = line.getStations();
        for (int i = 0; i < stations.length; i++){
            System.out.printf("Station %s%n", stations[i].getName());
            openDoors();

            if (passenger != null && stations[i].equals(destination)){
                alight(passenger);
                System.out.printf("%s %s arrived at destination station %s%n",
                        passenger.getFirstname(), passenger.getSurname(), stations[i].getName());

                if (!continueToTerminus) {
                    return stations[i];
                }
            }

            if (i < stations.length - 1) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("Caution door's is closing, next station is %s%n", stations[i + 1].getName());
                closeDoors();
            }
        }
        return stations[stations.length - 1];
    }


    public Station enterTheTrain(LocalTime departureTime, Passenger passenger,
                                 Line line, Station destinationStation){
        if (LocalTime.now().isBefore(departureTime)){
            System.out.printf("%s %s is waiting for train at %s%n",
                    passenger.getFirstname(), passenger.getSurname(), departureTime);
        }
        System.out.printf("Train is arrived.%n");
        board(passenger);

        go(line, destinationStation, passenger, true);

        return destinationStation;
    }


}
