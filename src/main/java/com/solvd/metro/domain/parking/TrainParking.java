package com.solvd.metro.domain.parking;

import com.solvd.metro.application.annotation.SetLocation;
import com.solvd.metro.domain.train.CarriageStatus;
import com.solvd.metro.domain.train.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TrainParking<T extends Train> {

    private static final AtomicLong idCounter = new AtomicLong(0);
    private static final Logger logger = LogManager.getLogger(TrainParking.class);

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
            logger.error("Train to park can't be null");
            throw new IllegalArgumentException("Train to park can't be null");
        }
        if (parkedTrains.contains(train)) {
            logger.info("Train {} is already parked here", train.getCode());
            return false;
        }
        train.getCarriages().forEach(carriage ->
                carriage.setCarriageStatus(CarriageStatus.PARKED));

        parkedTrains.add(train);
        logger.info("Train {} was parked in {} parking located in {}" , train.getCode(),
                this.name , location);
        return true;
    }

    public boolean getTrainOutOfParking(T train) {
        if (train == null) {
            logger.error("Train to park can't be null");
            throw new IllegalArgumentException("Train to park can't be null");
        }
        if (!parkedTrains.contains(train)) {
            logger.info ("Train {}  is not parked here", train.getCode());
            return false;
        }
        train.getCarriages().forEach(carriage ->
                carriage.setCarriageStatus(CarriageStatus.ACTIVE));

        parkedTrains.remove(train);
        logger.info("Train {} was driven out from {} parking located in {}", train.getCode()
                , this.name , location);
        return true;
    }

    public boolean containsTrain(T train) {
        if (train == null) {
            logger.error("Train can't be null");
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
       logger.info("All trains were driven out from {} parking" , this.name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            logger.error("Parking name can't be null or empty");
            throw new IllegalArgumentException("Parking name can't be null or empty");
        }
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location == null || location.isBlank()) {
            logger.error("Parking location can't be null or empty");
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