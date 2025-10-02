package domain.train;

import application.exception.CarriageNotOperationalException;
import application.exception.NoEmployeeAssignedException;
import application.port.OpenClose;
import application.port.PassengerAction;
import application.port.PublicTransport;
import domain.people.employee.Driver;
import domain.people.passenger.Passenger;
import domain.route.Line;
import domain.station.Station;
import utils.MyDoublyLinkedList;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Train implements OpenClose, PublicTransport {

    private static long idCounter = 0;

    private long id;
    private String code;
    private List<Carriage> carriages;
    private int onboardCount;
    private byte acTemperature;
    boolean doorsClosed;
    private Driver driver;

    public Train(MyDoublyLinkedList<Carriage> carriages) {
        this.id = ++idCounter;
        this.carriages = new MyDoublyLinkedList<>();
        setCarriages(carriages);
        this.onboardCount = 0;
    }

    public Train(String code, MyDoublyLinkedList<Carriage> carriages) {
        this.id = ++idCounter;
        setCode(code);
        this.carriages = new MyDoublyLinkedList<>();
        setCarriages(carriages);
        this.onboardCount = 0;
    }

    @Override
    public void board(Passenger passenger) {
        open();
        onboardCount++;
        if (onboardCount > getCapacity()) {
            System.err.printf("The train is full %n");
            onboardCount--;
            close();
            return;
        }
        System.out.printf("%s%s passenger just boarded, total amount of passengers %d%n",
                passenger.getFirstname(), passenger.getSurname(), onboardCount);
    }

    @Override
    public void alight(Passenger passenger) {
        onboardCount--;
        System.out.printf("%s%s alight from train %n",
                passenger.getFirstname(), passenger.getSurname());
        close();
    }

    @Override
    public void open() {
        doorsClosed = false;
        System.out.printf("%d door's opened%n", getDoorsAmount());
    }

    @Override
    public void close() {
        doorsClosed = true;
        System.out.printf("%d door's closed%n", getDoorsAmount());
    }

    @Override
    public boolean isClosed() {
        return doorsClosed;
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

    public List<Carriage> getCarriages() {

        return carriages;
    }

    public void addCarriage(Carriage carriage) {
        if (carriage == null) {
            throw new IllegalArgumentException("Carriage to add can't be null.");
        }
        if (carriage.getCarriageStatus() != CarriageStatus.ACTIVE) {
            throw new CarriageNotOperationalException("Carriage can't be attached" +
                    " because carriage status = " + carriage.getCarriageStatus().toString());
        }
        this.carriages.add(carriage);
    }

    public void removeCarriage(Carriage carriage) {
        if (carriage == null) {
            throw new IllegalArgumentException("Carriage to remove can't be null.");
        }
        this.carriages.remove(carriage);
    }

    private void setCarriages(List<Carriage> carriages) {
        if (carriages == null) {
            throw new IllegalArgumentException("Carriages can't be null");
        }
        if (carriages.size() < 2) {
            throw new IllegalArgumentException("To make a train should add minimum 2 carriages.");
        }
        carriages
                .forEach(this::addCarriage);
    }

    @Override
    public Driver getDriver() {
        return driver;
    }

    @Override
    public void assignDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("You can't assign null instead of driver");
        }
        this.driver = driver;
    }

    public void removeDriver() {
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

    @Override
    public int getCapacity() {
        return carriages.stream()
                .mapToInt(Carriage::getCarriageTotalCapacity)
                .sum();
    }

    @Override
    public int getDoorsAmount() {
        return carriages.stream()
                .mapToInt(Carriage::getDoorCount)
                .sum();
    }

    public List<Carriage> getCariagesFromYear(int minimumYear){
      return  carriages.stream()
                .filter(carriage -> carriage.getProductionYear() >= minimumYear)
                .toList();
    }

    public List<Carriage> getCariagesByYearAndSeverityScore(int minimumYear, float minimumSeverityScore){
        return carriages.stream()
                .filter(carriage -> carriage.getProductionYear() >= minimumYear)
                .filter(carriage -> carriage.getSeverityScore() >= minimumSeverityScore)
                .toList();
    }

    private Station go(Line line, Station destination, Passenger passenger,
                       boolean continueToTerminus, PassengerAction alightAction) {
        if (line == null) {
            throw new IllegalArgumentException("Nowhere to go line is null.");
        }
        if (line.getStations() == null) {
            throw new IllegalArgumentException("Nowhere to go stations is null.");
        }
        if (driver == null) {
            throw new NoEmployeeAssignedException("To be able run metro system you" +
                    " should assign driver for each train!");
        }

        List<Station> stations = line.getStations();
        for (int i = 0; i < stations.size(); i++) {
            System.out.printf("Station %s%n", stations.get(i).getName());
            open();

            if (passenger != null && stations.get(i).equals(destination)) {

                alightAction.execute(passenger, destination);
                if (!continueToTerminus) {
                    return stations.get(i);
                }
            }

            if (i < stations.size() - 1) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("Caution door's is closing, next station is %s%n",
                        stations.get(i + 1).getName());
                close();
            }
        }
        return stations.getLast();
    }

    public Station enterTheTrain(LocalTime departureTime, Passenger passenger,
                                 Line line, Station destinationStation,
                                 Station onboardingStation, PassengerAction boardPassenger) {
        if (LocalTime.now().isBefore(departureTime)) {
            System.out.printf("%s %s is waiting for train at %s%n",
                    passenger.getFirstname(), passenger.getSurname(), departureTime);
        }
        System.out.printf("Train is arrived.%n");



        boardPassenger.execute(passenger, onboardingStation);

        PassengerAction alightPassenger = (passenger1, station) ->{
            alight(passenger1);
            System.out.printf("%s %s arrived at destination station %s%n",
                    passenger1.getFirstname(), passenger1.getSurname(), station.getName());
        };

        go(line, destinationStation, passenger, true, alightPassenger);

        return destinationStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train train)) return false;
        return id == train.id &&
                Objects.equals(code, train.code) &&
                Objects.equals(carriages, train.carriages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, carriages);
    }
}