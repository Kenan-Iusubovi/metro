package com.solvd.metro.domain.route;


import com.solvd.metro.application.exception.ScheduleException;
import com.solvd.metro.application.port.TrainAssignmentStrategy;
import com.solvd.metro.domain.station.Station;
import com.solvd.metro.domain.system.Metro;
import com.solvd.metro.domain.train.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Schedule {

    private static long idCounter = 0;
    private static final Logger logger = LogManager.getLogger(Schedule.class);

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
            logger.error("Time can't be null.");
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
            logger.error("Line can't be null.");
            throw new ScheduleException("Line can't be null.");
        }

        this.line = line;
    }

    public List<String> getDepartureTimes() {
        return departureTimes.entrySet().stream()
                .flatMap(entry ->
                        entry.getValue().stream()
                                .map(time -> entry.getKey().getName() + " --> " + time)
                )
                .sorted()
                .toList();
    }

    public void setDepartureTimes(Map<Station, NavigableSet<LocalTime>> departureTimes) {
        if (departureTimes == null) {
            logger.error("Departure times can't be null.");
            throw new ScheduleException("Departure times can't be null.");
        }

        List<Station> stations = line.getStations();
        if (stations == null || stations.isEmpty()) {
            logger.error("Line must have stations before setting departures.");
            throw new ScheduleException("Line must have stations before setting departures.");
        }

        if (departureTimes.size() != stations.size()) {
            logger.error("DepartureTimes must match number of stations.");
            throw new ScheduleException("DepartureTimes must match number of stations.");
        }

        stations.stream()
                .filter(station -> departureTimes.get(station) == null)
                .findFirst()
                .ifPresent(station -> {
                    logger.error("Station {} has no times.", station.getName() );
                    throw new ScheduleException("Station " + station.getName() + " has no times.");
                });

        this.departureTimes = departureTimes;
    }

    public Train getTrainByDepartureTime(Station station, LocalTime departureTime) {
        if (station == null) {
            logger.error("Station can't be null.");
            throw new ScheduleException("Station can't be null.");
        }

        if (departureTime == null) {
            logger.error("Departure time can't be null.");
            throw new ScheduleException("Departure time can't be null.");
        }

        NavigableSet<LocalTime> times = departureTimes.get(station);

        List<Train> trains = line.getTrains();

        TrainAssignmentStrategy trainAssignmentStrategy = () -> {
            int index = new ArrayList<>(times).indexOf(departureTime);

            return trains.get(index % trains.size());
        };

        return findTrain(trainAssignmentStrategy, times, trains, departureTime);
    }

    private Train findTrain(TrainAssignmentStrategy trainAssignmentStrategy,
                            NavigableSet<LocalTime> times, List<Train> trains, LocalTime departureTime) {
        if (times == null || !times.contains(departureTime)) {
            return null;
        }
        if (trains == null || trains.isEmpty()) {
            return null;
        }
        logger.info("Searching for the available trains ....");
        return trainAssignmentStrategy.find();
    }

    public static final class ScheduleGenerator {

        private ScheduleGenerator() {
        }

        public static Schedule generateDailySchedule(Line line, Metro metro,
                                                     int minutesBetweenStops, int tripsCount) {
            if (line == null) {
                logger.error("Line can't be null.");
                throw new ScheduleException("Line can't be null.");
            }
            if (metro == null) {
                logger.error("Metro can't be null.");
                throw new ScheduleException("Metro can't be null.");
            }
            if (minutesBetweenStops <= 0) {
                logger.error("Minutes between stops must be positive.");
                throw new ScheduleException("Minutes between stops must be positive.");
            }
            if (tripsCount <= 0) {
                logger.error("Train count must be positive.");
                throw new ScheduleException("Train count must be positive.");
            }

            LocalTime serviceStart = metro.getServiceStartAt();
            LocalTime serviceEnd = metro.getServiceEndAt();

            if (serviceStart == null || serviceEnd == null) {
                logger.error("Metro service hours must be set.");
                throw new ScheduleException("Metro service hours must be set.");
            }
            if (serviceStart.isAfter(serviceEnd)) {
                logger.error("Service start time must be before end time.");
                throw new ScheduleException("Service start time must be before end time.");
            }

            List<Station> stations = line.getStations();
            if (stations == null || stations.isEmpty()) {
                logger.error("Line must have stations.");
                throw new ScheduleException("Line must have stations.");
            }

            Map<Station, NavigableSet<LocalTime>> departureTimes = new LinkedHashMap<>();
            long totalServiceMinutes = ChronoUnit.MINUTES.between(serviceStart, serviceEnd);
            long intervalMinutes = totalServiceMinutes / tripsCount;

            IntStream.range(0, stations.size()).forEach(index -> {
                Station station = stations.get(index);

                int stationOfsettMinutes = index * minutesBetweenStops;

                NavigableSet<LocalTime> times = Stream.iterate(
                                serviceStart.plusMinutes(stationOfsettMinutes),
                                nextTrainTine -> nextTrainTine.plusMinutes(intervalMinutes)
                        )
                        .limit(tripsCount)
                        .filter(stationTime -> !stationTime.isAfter(serviceEnd))
                        .map(ScheduleGenerator::roundToNearestFiveMinutes)
                        .filter(roundedTime -> !roundedTime.isAfter(serviceEnd))
                        .collect(Collectors.toCollection(TreeSet::new));

                departureTimes.put(station, times);
            });

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
        logger.info("Schedule for Line: {}", line.getName());
        departureTimes.entrySet().stream()
                .forEach(entry ->
                        logger.info("Station: {} = {}", entry.getKey().getName(), entry.getValue()));
    }
}