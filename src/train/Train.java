package train;

import people.passenger.Passenger;
import people.worker.Worker;
import people.worker.WorkerProfession;
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
    private Worker driver;

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
            System.err.println("The train is full");
            onboardCount--;
            return false;

        }
        System.out.println( passenger.getFirstname() + passenger.getSurname() +
                " passenger just unboarded, " +
                "total amount of passengers " + onboardCount);
        return true;
    }

    private void alight(Passenger passenger) {
        closeDoors();
        onboardCount --;
        System.out.println(passenger.getFirstname() + passenger.getSurname() +
                "alight from train");
        closeDoors();
    }

    private void openDoors(){
        isDoorsOpen = true;
        System.out.println(getDoorsAmount() + " door's opened");
    }

    private void closeDoors(){
        isDoorsOpen = false;
        System.out.println(getDoorsAmount() + " door's closed");
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

    public Worker getDriver() {
        return driver;
    }

    public void assignDriver(Worker driver) {
        if (driver == null){
            throw new IllegalArgumentException("You can't assign null instead of driver");
        }
        if (!WorkerProfession.DRIVER.equals(driver.getProfession())){
            throw new IllegalArgumentException("You can assign only drivers to drive a train");
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

    private Station go(Line line, Station destination){
        if (line == null){
            throw new IllegalArgumentException("Nowhere to go line is null.");
        }
        if (line.getStations() == null){
            throw new IllegalArgumentException("Nowhere to go stations is null.");
        }
        if (driver == null){
            throw new RuntimeException("To be able run metro system" +
                    " you should assign driver for each train!");
        }
        Station[] stations = line.getStations();
        for (int i = 0; i < stations.length; i++){
            System.out.println("Station " + stations[i].getName());
            openDoors();
            if (stations[i].equals(destination)){
                return stations[i];
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Caution door's is closing," +
                    " next station is " + stations[i+1].getName());
            closeDoors();
        }
        return null;
    }
    public Station enterTheTrain(LocalTime departureTime, Passenger passenger,
                              Line line, Station destinationStation){
        if (LocalTime.now().isBefore(departureTime)){
            System.out.println(passenger.getFirstname() + " " + passenger.getSurname() +
                    " is waiting for train at " + departureTime);
        }
        System.out.println("Train is arrived.");
        board(passenger);
        Station station = go(line, destinationStation);
        alight(passenger);
        return station;
    }
}
