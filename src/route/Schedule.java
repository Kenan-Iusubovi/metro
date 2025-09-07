package route;

import station.Station;
import system.Metro;
import train.Train;

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

    private int stationIndex(Station station) {
        if (station == null) throw new IllegalArgumentException("Station can't be null.");
        Station[] sts = line.getStations();
        for (int i = 0; i < sts.length; i++) {
            if (sts[i] != null && sts[i].getCode() == station.getCode()) {
                return i;
            }
        }
        return -1;
    }

    public LocalTime nextDeparture(Station station) {
        return nextDepartureAt(station, LocalTime.now());
    }

    public LocalTime nextDepartureAt(Station station, LocalTime at) {
        if (at == null) throw new IllegalArgumentException("Time can't be null.");

        int idx = stationIndex(station);
        if (idx == -1) return null;

        LocalTime[] times = departureTimes[idx];
        if (times == null || times.length == 0) return null;

        LocalTime candidate = null;
        LocalTime earliest  = null;

        for (int i = 0; i < times.length; i++) {
            LocalTime t = times[i];
            if (t == null) continue;

            if (earliest == null || t.isBefore(earliest)) {
                earliest = t;
            }
            if (!t.isBefore(at)) {
                if (candidate == null || t.isBefore(candidate)) {
                    candidate = t;
                }
            }
        }
        return (candidate != null) ? candidate : earliest;
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
        Station[] stations = line.getStations();
        if (stations == null) {
            throw new IllegalStateException("Line must have stations before setting departures.");
        }
        if (departureTimes.length != stations.length) {
            throw new IllegalArgumentException("DepartureTimes rows must match stations length.");
        }

        LocalTime[][] copy = new LocalTime[departureTimes.length][];
        for (int i = 0; i < departureTimes.length; i++) {
            LocalTime[] row = departureTimes[i];
            if (row == null) {
                copy[i] = new LocalTime[0];
                continue;
            }
            LocalTime prev = null;
            for (int k = 0; k < row.length; k++) {
                LocalTime t = row[k];
                if (t == null) {
                    throw new IllegalArgumentException("Null time at row " + i + ", col " + k);
                }
                if (prev != null && t.isBefore(prev)) {
                    throw new IllegalArgumentException("Times must be ascending at row " + i);
                }
                prev = t;
            }
            LocalTime[] rowCopy = new LocalTime[row.length];
            System.arraycopy(row, 0, rowCopy, 0, row.length);
            copy[i] = rowCopy;
        }
        this.departureTimes = copy;
    }

    public Train getTrainByDepartureTime(Station station, LocalTime departureTime) {
        if (station == null) throw new IllegalArgumentException("Station can't be null.");
        if (departureTime == null) throw new IllegalArgumentException("Departure time can't be null.");

        int stationIndex = stationIndex(station);
        if (stationIndex < 0) return null;

        LocalTime[] stationTimes = departureTimes[stationIndex];
        if (stationTimes == null || stationTimes.length == 0) return null;

        for (int i = 0; i < stationTimes.length; i++) {
            LocalTime t = stationTimes[i];
            if (t != null && t.equals(departureTime)) {
                Train[] trains = line.getTrains();
                if (trains != null && trains.length > 0) {
                    return trains[i % trains.length];
                }
                return null;
            }
        }
        return null;
    }

    public static final class Generator {

        public static Schedule fixedHeadway(Line line,
                                            LocalTime firstAtOrigin,
                                            LocalTime lastAtOrigin,
                                            int headwayMin,
                                            int perStationOffsetMin) {
            if (line == null) throw new IllegalArgumentException("Line can't be null.");
            if (firstAtOrigin == null || lastAtOrigin == null)
                throw new IllegalArgumentException("Times can't be null.");
            if (headwayMin <= 0) throw new IllegalArgumentException("headwayMin must be > 0");
            if (perStationOffsetMin < 0) perStationOffsetMin = 0;

            Station[] stations = line.getStations();
            LocalTime[][] departures = new LocalTime[stations.length][];

            int firstMin = firstAtOrigin.getHour() * 60 + firstAtOrigin.getMinute();
            int lastMin  =  lastAtOrigin.getHour()  * 60 +  lastAtOrigin.getMinute();

            for (int i = 0; i < stations.length; i++) {
                if (stations[i] == null) {
                    departures[i] = new LocalTime[0];
                    continue;
                }
                int start = firstMin + i * perStationOffsetMin;
                if (start > lastMin) {
                    departures[i] = new LocalTime[0];
                    continue;
                }
                int count = ((lastMin - start) / headwayMin) + 1;
                LocalTime[] row = new LocalTime[count];
                for (int k = 0; k < count; k++) {
                    int m = start + k * headwayMin;
                    row[k] = LocalTime.of(m / 60, m % 60);
                }
                departures[i] = row;
            }
            return new Schedule(line, departures);
        }

        public static Schedule fromTrainCount(Line line, Metro metro,
                                              int trains,
                                              int perStationOffsetMin,
                                              int runMinBetweenStations,
                                              int dwellMidSec,
                                              int terminalTurnMin) {
            if (line == null) throw new IllegalArgumentException("Line can't be null.");
            if (metro.getServiceStartAt() == null || metro.getServiceEndAt() == null)
                throw new IllegalArgumentException("Times can't be null.");
            if (trains <= 0) throw new IllegalArgumentException("trains must be > 0");
            if (perStationOffsetMin < 0) perStationOffsetMin = 0;

            int stationCount = (line.getStations() == null) ? 0 : line.getStations().length;
            double dwellMidMin = Math.ceil(Math.max(0, dwellMidSec) / 60.0);

            int headwayMin = headwayFromTrains(
                    stationCount,
                    Math.max(1, runMinBetweenStations),
                    dwellMidMin,
                    Math.max(0, terminalTurnMin),
                    trains
            );
            return fixedHeadway(line, metro.getServiceStartAt(),
                    metro.getServiceEndAt(), headwayMin, perStationOffsetMin);
        }

        private static int headwayFromTrains(int stationCount,
                                             double runMinBetweenStations,
                                             double dwellMidMin,
                                             double terminalTurnMin,
                                             int trains) {
            if (stationCount < 2) return 1;
            int segments = stationCount - 1;
            int intermediates = Math.max(0, stationCount - 2);
            double oneWay = segments * runMinBetweenStations + intermediates * dwellMidMin;
            double cycle  = 2 * oneWay + 2 * terminalTurnMin;
            return Math.max(1, (int) Math.ceil(cycle / trains));
        }

        public static void printTimetable(Line line, LocalTime[][] departures) {
            Station[] stations = (line == null) ? null : line.getStations();
            if (stations == null || departures == null) return;
            for (int i = 0; i < stations.length; i++) {
                String name = (stations[i] == null) ? ("<null-" + i + ">") : stations[i].getName();
                System.out.print(name + ": ");
                LocalTime[] row = (i < departures.length && departures[i] != null) ? departures[i] : new LocalTime[0];
                for (int k = 0; k < row.length; k++) {
                    LocalTime t = row[k];
                    if (t != null) {
                        String hh = (t.getHour() < 10 ? "0" : "") + t.getHour();
                        String mm = (t.getMinute() < 10 ? "0" : "") + t.getMinute();
                        System.out.print(hh + ":" + mm + (k < row.length - 1 ? ", " : ""));
                    }
                }
                System.out.println();
            }
        }
    }
}
