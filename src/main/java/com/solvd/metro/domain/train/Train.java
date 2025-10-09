package com.solvd.metro.domain.train;

import com.solvd.metro.application.exception.CarriageNotOperationalException;
import com.solvd.metro.application.exception.NoEmployeeAssignedException;
import com.solvd.metro.application.port.OpenClose;
import com.solvd.metro.application.port.PassengerAction;
import com.solvd.metro.application.port.PublicTransport;
import com.solvd.metro.domain.people.employee.Driver;
import com.solvd.metro.domain.people.passenger.Passenger;
import com.solvd.metro.domain.route.Line;
import com.solvd.metro.domain.station.Station;
import com.solvd.metro.domain.ticket.Ticket;
import com.solvd.metro.utils.MyDoublyLinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Train implements OpenClose, PublicTransport {

    private static long idCounter = 0;
    private static final Logger logger = LogManager.getLogger(Train.class);

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
            logger.info("The train is full ");
            onboardCount--;
            close();
            return;
        }
        logger.info("{}{} passenger just boarded, total amount of passengers {}",
                passenger.getFirstname(), passenger.getSurname(), onboardCount);
    }

    @Override
    public void alight(Passenger passenger) {
        onboardCount--;
        logger.info("{} {} alight from train ",
                passenger.getFirstname(), passenger.getSurname());
        close();
    }

    @Override
    public void open() {
        doorsClosed = false;
        logger.info("{} door's opened", getDoorsAmount());
    }

    @Override
    public void close() {
        doorsClosed = true;
        logger.info("{} door's closed", getDoorsAmount());
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
            logger.error("Code can't be null or empty.");
            throw new IllegalArgumentException("Code can't be null or empty.");
        }
        this.code = code;
    }

    public List<Carriage> getCarriages() {

        return carriages;
    }

    public void addCarriage(Carriage carriage) {
        if (carriage == null) {
            logger.error("Carriage to add can't be null.");
            throw new IllegalArgumentException("Carriage to add can't be null.");
        }
        if (carriage.getCarriageStatus() != CarriageStatus.ACTIVE) {
            logger.error("Carriage can't be attached because carriage status = {}"
                    , carriage.getCarriageStatus().toString());
            throw new CarriageNotOperationalException("Carriage can't be attached" +
                    " because carriage status = " + carriage.getCarriageStatus().toString());
        }
        this.carriages.add(carriage);
    }

    public void removeCarriage(Carriage carriage) {
        if (carriage == null) {
            logger.error("Carriage to remove can't be null.");
            throw new IllegalArgumentException("Carriage to remove can't be null.");
        }
        this.carriages.remove(carriage);
    }

    private void setCarriages(List<Carriage> carriages) {
        if (carriages == null) {
            logger.error("Carriages can't be null");
            throw new IllegalArgumentException("Carriages can't be null");
        }
        if (carriages.size() < 2) {
            logger.error("To make a train should add minimum 2 carriages.");
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
            logger.error("You can't assign null instead of driver");
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
        if (temperature < 16 || temperature > 32) {
            logger.error("AC temperature can be set between 16 C° - 32 C°");
            throw new IllegalArgumentException("AC temperature can be set between 16 C° - 32 C°");
        }

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

    public List<Carriage> getCariagesFromYear(int minimumYear) {
        return carriages.stream()
                .filter(carriage -> carriage.getProductionYear() >= minimumYear)
                .toList();
    }

    public List<Carriage> getCariagesByYearAndSeverityScore(int minimumYear, float minimumSeverityScore) {
        return carriages.stream()
                .filter(carriage -> carriage.getProductionYear() >= minimumYear)
                .filter(carriage -> carriage.getSeverityScore() >= minimumSeverityScore)
                .toList();
    }

    private Station go(Line line, Station destination, Passenger passenger,
                       boolean continueToTerminus, PassengerAction alightAction) {
        if (line == null) {
            logger.error("Nowhere to go line is null.");
            throw new IllegalArgumentException("Nowhere to go line is null.");
        }
        if (line.getStations() == null) {
            logger.error("Nowhere to go stations is null.");
            throw new IllegalArgumentException("Nowhere to go stations is null.");
        }
        if (driver == null) {
            logger.error("To be able run metro system you" +
                    " should assign driver for each train!");
            throw new NoEmployeeAssignedException("To be able run metro system you" +
                    " should assign driver for each train!");
        }

        List<Station> stations = line.getStations();
        for (int i = 0; i < stations.size(); i++) {
            logger.info("Station {}", stations.get(i).getName());
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
                logger.info("Caution door's is closing, next station is {}",
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
            logger.info("{} {} is waiting for train at {}",
                    passenger.getFirstname(), passenger.getSurname(), departureTime);
        }
        logger.info("Train is arrived.");


        boardPassenger.execute(passenger, onboardingStation);

        PassengerAction alightPassenger = (passenger1, station) -> {
            alight(passenger1);
           logger.info("{} {} arrived at destination station {}",
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