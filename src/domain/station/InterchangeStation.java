package domain.station;

import domain.people.passenger.Passenger;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class InterchangeStation extends Station {

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
            throw new IllegalArgumentException("Transfer station can't be null.");
        }
        this.transferStations.add(station);
    }

    public void removeTransferStation(Station station) {
        if (station == null) {
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
            throw new IllegalArgumentException("Passenger and target station can't be null.");
        }
        if (!canTransferTo(targetStation)) {
            throw new IllegalStateException("This interchange does not connect to the target station.");
        }
        System.out.printf(
                "Passenger %s %s changed the line from station %s to station %s.%n",
                passenger.getFirstname(), passenger.getSurname(),
                this.getName(), targetStation.getName()
        );
        return targetStation;
    }

    private void setTransferStations(Set<Station> stations) {
        if (stations.isEmpty()) {
            throw new IllegalArgumentException("No station to add as interchange station");
        }
        stations.forEach(this::addTransferStation);
    }

    public Station changeStation(Passenger passenger, Station targetStation) {
        return changeLine(passenger, targetStation);
    }
}
