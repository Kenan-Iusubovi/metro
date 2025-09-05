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

    public Line(Long code, String name, String color) {
        this.id = ++idCounter;
        setCode(code);
        setName(name);
        setColor(color);
        setActive(true);
        this.stations = new Station[0];
        this.trains = new Train[0];
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

    public int inServiceTrainCount() {
        int count = 0;
        for (int i = 0; i < trains.length; i++){
            if (trains[i] != null && trains[i].isInService()){
                count++;
            }
        }
        return count;
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

    public void setCode(Long code) {
        if (code == null || code <= 0) {
            throw new IllegalArgumentException("Code can't be null or negative.");
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
