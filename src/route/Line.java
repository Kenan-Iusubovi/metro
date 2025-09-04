package route;

import station.Station;
import train.Train;

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

    public Line(long code, String name, List<Station> stations,
                List<Train> assignedTrains, boolean isActive, String color) {
        this.id = ++idCounter;
        this.code = code;
        this.name = name;
        this.stations = stations;
        this.assignedTrains = assignedTrains;
        this.isActive = isActive;
        this.color = color;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Train> getAssignedTrains() {
        return assignedTrains;
    }

    public void setAssignedTrains(List<Train> assignedTrains) {
        this.assignedTrains = assignedTrains;
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
        this.color = color;
    }
}
