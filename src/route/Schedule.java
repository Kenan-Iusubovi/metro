package route;

import station.Station;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Schedule {

    private static long idCounter = 0;

    private long id;

    private Line line;

    private Map<Station, List<LocalTime>> departuresByStation;

    public Schedule(Line line, Map<Station, List<LocalTime>> departuresByStation) {

        this.id = ++idCounter;
        this.line = line;
        this.departuresByStation = departuresByStation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Map<Station, List<LocalTime>> getDeparturesByStation() {
        return departuresByStation;
    }

    public void setDeparturesByStation(Map<Station, List<LocalTime>> departuresByStation) {
        this.departuresByStation = departuresByStation;
    }
}
