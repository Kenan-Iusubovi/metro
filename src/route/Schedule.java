package route;

import station.Station;

import java.time.LocalTime;

public class Schedule {

    private static long idCounter = 0;
    private Long id;
    private Line line;
    private LocalTime[][] departureTimes;

    public Schedule(Line line, LocalTime[][] departureTimes) {
        this.id = ++idCounter;
        setLine(line);
        setDepartureTimes(departureTimes);
    }

    public LocalTime nextDeparture(Station station, LocalTime now) {
        if (station == null || now == null) {
            throw new IllegalArgumentException("Station or Time can't be null.");
        }
        int idx = -1;
        for (int i = 0; i < line.getStations().length; i++) {
            if (line.getStations()[i] != null &&
                    line.getStations()[i].getCode() == station.getCode()) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            return null;
        }
        LocalTime[] times = departureTimes[idx];
        if (times == null) {
            return null;
        }
        for (int i = 0; i < times.length; i++) {
            if (times[i] != null && !times[i].isBefore(now)) {
                return times[i];
            }
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        if (line == null) {
            throw new IllegalArgumentException("Line can't be null.");
        }
        this.line = line;
    }

    public LocalTime[][] getDepartureTimes() {
        return departureTimes;
    }

    public void setDepartureTimes(LocalTime[][] departureTimes) {
        if (departureTimes == null) {
            throw new IllegalArgumentException("Departure times can't be null.");
        }
        if (line.getStations() == null) {
            throw new IllegalStateException("Stations can't be null.");
        }
        if (departureTimes.length != line.getStations().length) {
            throw new IllegalArgumentException("DepartureTimes rows must match stations length.");
        }
        this.departureTimes = departureTimes;
    }
}
