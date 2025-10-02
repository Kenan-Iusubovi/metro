package domain.parking;

import application.annotation.SetLocation;
import domain.train.CarriageStatus;
import domain.train.Train;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TrainParking<T extends Train> {

    private static AtomicLong idCounter = new AtomicLong(0);

    private Long id;
    private String name;
    @SetLocation()
    private String location;
    private List<T> parkedTrains;

    public TrainParking(String name, String location) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.location = location;
        this.parkedTrains = new ArrayList<>();
    }

    public TrainParking(String name) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.parkedTrains = new ArrayList<>();
    }

    public boolean parkTrain(T train) {
        if (train == null) {
            throw new IllegalArgumentException("Train to park can't be null");
        }
        if (parkedTrains.contains(train)) {
            System.out.println("Train " + train.getCode() + " is already parked here");
            return false;
        }
        train.getCarriages().forEach(carriage ->
                carriage.setCarriageStatus(CarriageStatus.PARKED));

        parkedTrains.add(train);
        System.out.println("Train " + train.getCode() + " was parked in " + this.name + " parking");
        return true;
    }

    public boolean getTrainOutOfParking(T train) {
        if (train == null) {
            throw new IllegalArgumentException("Train to park can't be null");
        }
        if (!parkedTrains.contains(train)) {
            System.out.println("Train " + train.getCode() + " is not parked here");
            return false;
        }
        train.getCarriages().forEach(carriage ->
                carriage.setCarriageStatus(CarriageStatus.ACTIVE));

        parkedTrains.remove(train);
        System.out.println("Train " + train.getCode() + " was driven out from "
                + this.name + " parking");
        return true;
    }

    public boolean containsTrain(T train) {
        if (train == null) {
            throw new IllegalArgumentException("Train can't be null");
        }
        return parkedTrains.contains(train);
    }

    public int getParkedTrainCount() {
        return parkedTrains.size();
    }

    public boolean isEmpty() {
        return parkedTrains.isEmpty();
    }

    public void clearParking() {
        parkedTrains.forEach(
                train -> train.getCarriages().forEach(
                        carriage -> carriage.setCarriageStatus(CarriageStatus.ACTIVE)
                )
        );

        parkedTrains.clear();
        System.out.println("All trains were driven out from " + this.name + " parking");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Parking name can't be null or empty");
        }
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Parking location can't be null or empty");
        }
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TrainParking<?> other = (TrainParking<?>) obj;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "TrainParking{id=" + id + ", name='" + name + "', location='" + location +
                "', parkedTrains=" + parkedTrains.size() + "}";
    }
}