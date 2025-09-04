package route;

import station.Station;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Schedule {

    private long id;

    private Line line;

    private Map<Station, List<LocalTime>> departuresByStation;

}
