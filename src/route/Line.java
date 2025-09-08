package route;

import station.Station;
import train.Train;
import utils.ArrayUtils;

public class Line {

    private static long idCounter = 0;

    private Long id;
    private Long code;
    private String name;
    private String color;
    private boolean active;
    private Station[] stations;
    private Train[] trains;

    public Line(long code, String name, String color) {
        this.id = ++idCounter;
        setCode(code);
        setName(name);
        setColor(color);
        setActive(true);
        this.stations = new Station[0];
        this.trains = new Train[0];
    }

    public Line(long code, String name, String color, Station[] stations) {
        this.id = ++idCounter;
        setCode(code);
        setName(name);
        setColor(color);
        setActive(true);
        this.stations = new Station[0];
        this.trains = new Train[0];
        setStations(stations);
    }

    public boolean hasStation(Station station) {
        if (station == null){
            return false;
        }
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] != null && stations[i].getCode() == station.getCode()){
                return true;
            }
        }
        return false;
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

    public Station[] getStations() {
        return stations;
    }

    public void addStation(Station station) {
        if (station == null){
            throw new IllegalArgumentException("Station can't be null.");
        }
        this.stations = (Station[]) ArrayUtils.add(this.stations, station);
    }

    public void removeStation(Station station) {
        if (station == null){
            throw new IllegalArgumentException("Station can't be null.");
        }
        this.stations = (Station[]) ArrayUtils.delete(this.stations, station);
    }

    private void setStations(Station[] stations){
        if (stations == null) {
            throw new IllegalArgumentException("Stations can't be null");
        }
        if (stations.length < 2){
            throw new IllegalArgumentException("To make a line should add minimum 2 stations.");
        }
        for (Station station : stations){
            addStation(station);
        }
    }

    public Train[] getTrains() {
        return trains;
    }

    public void addTrain(Train train) {
        if (train == null){
            throw new IllegalArgumentException("Train can't be null.");
        }
        this.trains = (Train[]) ArrayUtils.add(this.trains, train);
    }

    public void removeTrain(Train train) {
        if (train == null){
            throw new IllegalArgumentException("Train can't be null.");
        }
        this.trains = (Train[]) ArrayUtils.delete(this.trains, train);
    }
}
