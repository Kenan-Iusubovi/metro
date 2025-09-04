package route;

import station.Station;

import java.time.LocalTime;

public class Schedule {


    private static long idCounter = 0;

    private long id;

    private Line line;

    private Station[] stations;

    private LocalTime[][] departureTimes;

    public Schedule(Line line, LocalTime[][] departureTimes) {

        this.id = ++idCounter;
        setLine(line);
        this.stations = line.getStations();
        setDepartureTimes(departureTimes);
    }

    public LocalTime nextDeparture(Station station, LocalTime now) {

        if (station == null || now == null)
            return null;

        int idx = -1;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] != null && stations[i].getCode() == station.getCode()) {
                idx = i;
                break;
            }
        }

        if (idx == -1) return null;

        LocalTime[] times = departureTimes[idx];
        if (times == null) return null;

        for (int i = 0; i < times.length; i++)
            if (times[i] != null && !times[i].isBefore(now))
                return times[i];

        return null;
    }

    public long getId() {

        return id;
    }

    public Line getLine() {

        return line;
    }

    public void setLine(Line line) {

        if (line == null)
            throw new IllegalArgumentException("line");

        this.line = line;
    }

    public Station[] getStations() {

        return stations;
    }

    public LocalTime[][] getDepartureTimes() {

        return departureTimes;
    }

    public void setDepartureTimes(LocalTime[][] departureTimes) {

        if (departureTimes == null)
            throw new IllegalArgumentException("departureTimes");

        if (stations == null)
            throw new IllegalStateException("stations not initialized");

        if (departureTimes.length != stations.length)
            throw new IllegalArgumentException("departureTimes rows must match stations length");

        this.departureTimes = departureTimes;
    }

    public void resyncStationsFromLine() {

        if (line == null)
            throw new IllegalStateException("line not set");

        this.stations = line.getStations();

        if (departureTimes == null || departureTimes.length != stations.length)
            throw new IllegalStateException("departureTimes not aligned with stations");
    }
}
