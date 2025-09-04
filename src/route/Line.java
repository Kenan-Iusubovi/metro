package route;

import station.Station;
import train.Train;

public class Line {

    private static long idCounter = 0;

    private long id;

    private long code;

    private String name;

    private String color;

    private boolean active;

    private Station[] stations;

    private Train[] trains;

    public Line(long code, String name, String color,
                Station[] stations, Train[] trains) {
        this.id = ++idCounter;
        setCode(code);
        setName(name);
        setColor(color);
        setStations(stations);
        setTrains(trains);
        setActive(true);
    }

    public boolean hasStation(Station s) {

        if (s == null) return false;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] != null && stations[i].getCode() == s.getCode()) return true;
        }
        return false;
    }

    public int inServiceTrainCount() {

        int c = 0;
        for (int i = 0; i < trains.length; i++)
            if (trains[i] != null && trains[i].) c++;
        return c;
    }

    public void activate() { this.active = true; }
    public void deactivate() { this.active = false; }

    public long getId() { return id; }
    public void setId(long id) { if (id <= 0) throw new IllegalArgumentException("id"); this.id = id; }

    public long getCode() { return code; }
    public void setCode(long code) { if (code <= 0) throw new IllegalArgumentException("code"); this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name"); this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { if (color == null || color.trim().isEmpty()) throw new IllegalArgumentException("color"); this.color = color; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Station[] getStations() { return stations; }
    public void setStations(Station[] stations) { if (stations == null) throw new IllegalArgumentException("stations"); this.stations = stations; }

    public Train[] getTrains() { return trains; }
    public void setTrains(Train[] trains) { if (trains == null) throw new IllegalArgumentException("trains"); this.trains = trains; }
}
