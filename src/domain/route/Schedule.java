package domain.route;

import application.exception.ScheduleException;
import application.port.TrainAssignmentStrategy;
import domain.station.Station;
import domain.system.Metro;
import domain.train.Train;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Schedule {

    private static long idCounter = 0;

    private Long id;
    private Line line;
    private Map<Station, NavigableSet<LocalTime>> departureTimes;

    public Schedule(Line line, Map<Station, NavigableSet<LocalTime>> departureTimes) {
        this.id = ++idCounter;
        setLine(line);
        setDepartureTimes(departureTimes);
    }

    public LocalTime nextDepartureTime(Station station, LocalTime at) {
        if (at == null) {
            throw new ScheduleException("Time can't be null.");
        }

        NavigableSet<LocalTime> times = departureTimes.get(station);
        if (times == null || times.isEmpty()) {
            return null;
        }

        LocalTime next = times.ceiling(at);

        if (next == null) {
            next = times.first();
        }

        return next;
    }

    public long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        if (line == null) {
            throw new ScheduleException("Line can't be null.");
        }

        this.line = line;
    }

    public Map<Station, NavigableSet<LocalTime>> getDepartureTimes() {
        return departureTimes;
    }

    public void setDepartureTimes(Map<Station, NavigableSet<LocalTime>> departureTimes) {
        if (departureTimes == null) {
            throw new ScheduleException("Departure times can't be null.");
        }

        List<Station> stations = line.getStations();
        if (stations == null || stations.isEmpty()) {
            throw new ScheduleException("Line must have stations before setting departures.");
        }

        if (departureTimes.size() != stations.size()) {
            throw new ScheduleException("DepartureTimes must match number of stations.");
        }

        for (Station station : stations) {
            NavigableSet<LocalTime> times = departureTimes.get(station);
            if (times == null) {
                throw new ScheduleException("Station " + station.getName() + " has no times.");
            }
        }

        this.departureTimes = departureTimes;
    }

    public Train getTrainByDepartureTime(Station station, LocalTime departureTime) {
        if (station == null) {
            throw new ScheduleException("Station can't be null.");
        }

        if (departureTime == null) {
            throw new ScheduleException("Departure time can't be null.");
        }

        NavigableSet<LocalTime> times = departureTimes.get(station);

        List<Train> trains = line.getTrains();

        TrainAssignmentStrategy trainAssignmentStrategy = () -> {
            int index = new ArrayList<>(times).indexOf(departureTime);

            return trains.get(index % trains.size());
        };

        return findTrain(trainAssignmentStrategy,times,trains,departureTime);
    }

    private Train findTrain(TrainAssignmentStrategy trainAssignmentStrategy,
                            NavigableSet<LocalTime> times, List<Train> trains, LocalTime departureTime){
        if (times == null || !times.contains(departureTime)) {
            return null;
        }
        if (trains == null || trains.isEmpty()) {
            return null;
        }
        System.out.println("Searching for the available trains ....");
         return trainAssignmentStrategy.find();
    }

    public static final class ScheduleGenerator {

        private ScheduleGenerator() {
        }

        public static Schedule generateDailySchedule(Line line, Metro metro,
                                                     int minutesBetweenStops, int tripsCount) {
            if (line == null) {
                throw new ScheduleException("Line can't be null.");
            }
            if (metro == null) {
                throw new ScheduleException("Metro can't be null.");
            }
            if (minutesBetweenStops <= 0) {
                throw new ScheduleException("Minutes between stops must be positive.");
            }
            if (tripsCount <= 0) {
                throw new ScheduleException("Train count must be positive.");
            }

            LocalTime serviceStart = metro.getServiceStartAt();
            LocalTime serviceEnd = metro.getServiceEndAt();

            if (serviceStart == null || serviceEnd == null) {
                throw new ScheduleException("Metro service hours must be set.");
            }
            if (serviceStart.isAfter(serviceEnd)) {
                throw new ScheduleException("Service start time must be before end time.");
            }

            List<Station> stations = line.getStations();
            if (stations == null || stations.isEmpty()) {
                throw new ScheduleException("Line must have stations.");
            }

            Map<Station, NavigableSet<LocalTime>> departureTimes = new LinkedHashMap<>();

            long totalServiceMinutes = ChronoUnit.MINUTES.between(serviceStart, serviceEnd);
            int intervalMinutes = (int) totalServiceMinutes / tripsCount;

            for (int i = 0; i < stations.size(); i++) {
                Station station = stations.get(i);
                NavigableSet<LocalTime> times = new TreeSet<>();

                int stationOffsetMinutes = i * minutesBetweenStops;

                for (int trainIndex = 0; trainIndex < tripsCount; trainIndex++) {
                    LocalTime baseTime = serviceStart.plusMinutes(
                            trainIndex * intervalMinutes);
                    LocalTime stationTime = baseTime.plusMinutes(stationOffsetMinutes);

                    if (!stationTime.isAfter(serviceEnd)) {
                        LocalTime roundedTime = roundToNearestFiveMinutes(stationTime);
                        if (!roundedTime.isBefore(serviceStart) && !roundedTime.
                                isAfter(serviceEnd)) {
                            times.add(roundedTime);
                        }
                    }
                }

                departureTimes.put(station, times);
            }
            printSchedule(line, departureTimes);
            return new Schedule(line, departureTimes);
        }

        private static LocalTime roundToNearestFiveMinutes(LocalTime time) {
            int minute = time.getMinute();
            int roundedMinute = (minute / 5) * 5;
            if (minute % 5 >= 3) {
                roundedMinute += 5;
            }
            if (roundedMinute >= 60) {
                return time.plusHours(1).withMinute(roundedMinute - 60).
                        withSecond(0).withNano(0);
            }
            return time.withMinute(roundedMinute).withSecond(0).withNano(0);
        }
    }

    private static void printSchedule(Line line, Map<Station,
            NavigableSet<LocalTime>> departureTimes) {
        System.out.println("Schedule for Line: " + line.getName());
        for (Station station : departureTimes.keySet()) {
            System.out.println("Station: " + station.getName() + " = " +
                    departureTimes.get(station));
        }
    }
}