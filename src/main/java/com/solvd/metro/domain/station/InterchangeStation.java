package com.solvd.metro.domain.station;


import com.solvd.metro.domain.people.passenger.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class InterchangeStation extends Station {

    private static final Logger logger = LogManager.getLogger(InterchangeStation.class);

    private Set<Station> transferStations;

    public InterchangeStation(String name, long code, boolean accessible,
                              byte platformCount, LocalDate builtOn) {
        super(name, code, accessible, platformCount, builtOn);
        this.transferStations = new HashSet<>();
    }

    public InterchangeStation(String name, long code, byte platformCount,
                              LocalDate builtOn, Set<Turnstile> turnstiles) {
        super(name, code, platformCount, builtOn, turnstiles);
        this.transferStations = new HashSet<>();
    }

    public Set<Station> getTransferStations() {
        return transferStations;
    }

    public InterchangeStation(String name, long code, boolean accessible,
                              byte platformCount, LocalDate builtOn, Set<Station> stations) {
        super(name, code, accessible, platformCount, builtOn);
        this.transferStations = new HashSet<>();
        setTransferStations(stations);
    }

    public InterchangeStation(String name, long code, byte platformCount, LocalDate builtOn,
                              Set<Turnstile> turnstiles, Set<Station> stations) {
        super(name, code, platformCount, builtOn, turnstiles);
        this.transferStations = new HashSet<>();
        setTransferStations(stations);
    }

    public InterchangeStation(String name, long code, boolean accessible,
                              byte platformCount, LocalDate builtOn, Station station) {
        super(name, code, accessible, platformCount, builtOn);
        this.transferStations = new HashSet<>();
        addTransferStation(station);
    }

    public void addTransferStation(Station station) {
        if (station == null) {
            logger.error("Transfer station can't be null.");
            throw new IllegalArgumentException("Transfer station can't be null.");
        }
        this.transferStations.add(station);
    }

    public void removeTransferStation(Station station) {
        if (station == null) {
            logger.error("Transfer station can't be null.");
            throw new IllegalArgumentException("Transfer station can't be null.");
        }
        this.transferStations.remove(station);
    }

    public boolean canTransferTo(Station station) {
        if (station == null) {
            return false;
        }
        return transferStations.contains(station);
    }

    public Station changeLine(Passenger passenger, Station targetStation) {
        if (passenger == null || targetStation == null) {
            logger.error("Passenger and target station can't be null.");
            throw new IllegalArgumentException("Passenger and target station can't be null.");
        }
        if (!canTransferTo(targetStation)) {
            logger.error("This interchange does not connect to the target station.");
            throw new IllegalStateException("This interchange does not connect to the target station.");
        }
        logger.info(
                "Passenger {} {} changed the line from station {} to station {}.%n",
                passenger.getFirstname(), passenger.getSurname(),
                this.getName(), targetStation.getName()
        );
        return targetStation;
    }

    private void setTransferStations(Set<Station> stations) {
        if (stations.isEmpty()) {
            logger.error("No station to add as interchange station");
            throw new IllegalArgumentException("No station to add as interchange station");
        }
        stations.forEach(this::addTransferStation);
    }

    public Station changeStation(Passenger passenger, Station targetStation) {
        return changeLine(passenger, targetStation);
    }
}
