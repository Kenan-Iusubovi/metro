package domain.route;

import domain.station.Station;
import domain.train.Train;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Line {

    private static long idCounter = 0;

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
            throw new IllegalArgumentException("Code can't be negative.");
        }
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be null or empty.");
        }
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.isBlank()) {
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
            throw new IllegalArgumentException("Station can't be null.");
        }
        this.stations.add(station);
    }

    public void removeStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("Station can't be null.");
        }
        this.stations.remove(station);
    }

    private void setStations(List<Station> stations) {
        if (stations == null) {
            throw new IllegalArgumentException("Stations can't be null");
        }
        if (stations.size() < 2) {
            throw new IllegalArgumentException("To make a line should add minimum 2 stations.");
        }
        for (Station station : stations) {
            addStation(station);
        }
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void addTrain(Train train) {
        if (train == null) {
            throw new IllegalArgumentException("Train can't be null.");
        }
        this.trains.add(train);
    }

    public void removeTrain(Train train) {
        if (train == null) {
            throw new IllegalArgumentException("Train can't be null.");
        }
        this.trains.remove(train);
    }
}
