package com.solvd.metro.domain.route;

import com.solvd.metro.domain.station.Station;
import com.solvd.metro.domain.train.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Line {

    private static long idCounter = 0;
    private static final Logger logger = LogManager.getLogger(Line.class);

    private Long id;
    private Long code;
    private String name;
    private String color;
    private boolean active;
    private List<Station> stations;
    private List<Train> trains;

    public Line(long code, String name, String color) {
        this.id = ++idCounter;
        setCode(code);
        setName(name);
        setColor(color);
        setActive(true);
        this.stations = new ArrayList<>();
        this.trains = new LinkedList<>();
    }

    public Line(long code, String name, String color, List<Station> stations) {
        this.id = ++idCounter;
        setCode(code);
        setName(name);
        setColor(color);
        setActive(true);
        this.stations = new ArrayList<>();
        this.trains = new LinkedList<>();
        setStations(stations);
    }

    public boolean hasStation(Station station) {
        return stations.contains(station);
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public long getId() {
        return id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        if (code <= 0) {
            logger.error("Code can't be negative.");
            throw new IllegalArgumentException("Code can't be negative.");
        }
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            logger.error("Name can't be null or empty.");
            throw new IllegalArgumentException("Name can't be null or empty.");
        }
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.isBlank()) {
            logger.error("Color can't be null or empty.");
            throw new IllegalArgumentException("Color can't be null or empty.");
        }
        this.color = color;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        if (station == null) {
            logger.error("Station can't be null.");
            throw new IllegalArgumentException("Station can't be null.");
        }
        this.stations.add(station);
    }

    public void removeStation(Station station) {
        if (station == null) {
            logger.error("Station can't be null.");
            throw new IllegalArgumentException("Station can't be null.");
        }
        this.stations.remove(station);
    }

    private void setStations(List<Station> stations) {
        if (stations == null) {
            logger.error("Stations can't be null");
            throw new IllegalArgumentException("Stations can't be null");
        }
        if (stations.size() < 2) {
            logger.error("To make a line should add minimum 2 stations.");
            throw new IllegalArgumentException("To make a line should add minimum 2 stations.");
        }
        stations.forEach(this::addStation);
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void addTrain(Train train) {
        if (train == null) {
            logger.error("Train can't be null.");
            throw new IllegalArgumentException("Train can't be null.");
        }
        this.trains.add(train);
    }

    public void removeTrain(Train train) {
        if (train == null) {
            logger.error("Train can't be null.");
            throw new IllegalArgumentException("Train can't be null.");
        }
        this.trains.remove(train);
    }
}
