package route;

import station.Station;
import train.Train;
import java.util.ArrayList;
import java.util.List;

public class Line {

    private static long idCounter = 0;

    private long id;
    private long code;
    private String name;
    private List<Station> stations;
    private List<Train> assignedTrains;
    private boolean isActive;
    private String color;

    public Line(long code, String name, boolean isActive, String color) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty!");
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color can't be empty!");
        }

        this.id = ++idCounter;
        this.code = code;
        this.name = name;
        this.isActive = isActive;
        this.color = color;
        this.stations = new ArrayList<>();
        this.assignedTrains = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty!");
        }
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        if (stations == null) {
            throw new IllegalArgumentException("Stations list can't be null!");
        }
        this.stations = stations;
    }

    public void addStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("Station can't be null!");
        }
        this.stations.add(station);
    }

    public List<Train> getAssignedTrains() {
        return assignedTrains;
    }

    public void setAssignedTrains(List<Train> assignedTrains) {
        if (assignedTrains == null) {
            throw new IllegalArgumentException("Assigned trains list can't be null!");
        }
        this.assignedTrains = assignedTrains;
    }

    public void assignTrain(Train train) {
        if (train == null) {
            throw new IllegalArgumentException("Train can't be null!");
        }
        this.assignedTrains.add(train);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color can't be empty!");
        }
        this.color = color;
    }
}