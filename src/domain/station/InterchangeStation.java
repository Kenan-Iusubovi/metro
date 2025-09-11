package domain.station;

import domain.people.passenger.Passenger;
import utils.ArrayUtils;

import java.time.LocalDate;

public class InterchangeStation extends Station {

    private Station[] transferStations;

    public InterchangeStation(String name, long code, boolean accessible,
                              byte platformCount, LocalDate builtOn) {
        super(name, code, accessible, platformCount, builtOn);
        this.transferStations = new Station[0];
    }

    public InterchangeStation(String name, long code, byte platformCount,
                              LocalDate builtOn, Turnstile[] turnstiles) {
        super(name, code, platformCount, builtOn, turnstiles);
        this.transferStations = new Station[0];
    }

    public Station[] getTransferStations() {
        return transferStations;
    }

    public InterchangeStation(String name, long code, boolean accessible,
                              byte platformCount, LocalDate builtOn, Station[] transferStations) {
        super(name, code, accessible, platformCount, builtOn);
        this.transferStations = new Station[0];
        setTransferStations(transferStations);
    }

    public InterchangeStation(String name, long code, byte platformCount, LocalDate builtOn,
                              Turnstile[] turnstiles, Station[] transferStations) {
        super(name, code, platformCount, builtOn, turnstiles);
        this.transferStations = new Station[0];
        setTransferStations(transferStations);
    }

    public InterchangeStation(String name, long code, boolean accessible,
                              byte platformCount, LocalDate builtOn, Station transferStation) {
        super(name, code, accessible, platformCount, builtOn);
        this.transferStations = new Station[0];
        addTransferStation(transferStation);
    }

    public InterchangeStation(String name, long code, byte platformCount, LocalDate builtOn,
                              Turnstile[] turnstiles, Station transferStation) {
        super(name, code, platformCount, builtOn, turnstiles);
        this.transferStations = new Station[0];
        addTransferStation(transferStation);
    }

    public void addTransferStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("Transfer domain.station can't be null.");
        }
        this.transferStations = (Station[]) ArrayUtils.add(this.transferStations, station);
    }

    public void removeTransferStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("Transfer domain.station can't be null.");
        }
        this.transferStations = (Station[]) ArrayUtils.delete(this.transferStations, station);
    }

    public boolean canTransferTo(Station station) {
        if (station == null) return false;
        for (Station s : transferStations) {
            if (s == station) return true;
        }
        return false;
    }

    public Station changeLine(Passenger passenger, Station targetStation) {
        if (passenger == null || targetStation == null) {
            throw new IllegalArgumentException("Passenger and target domain.station can't be null.");
        }
        if (!canTransferTo(targetStation)) {
            throw new IllegalStateException("This interchange does not connect to the target domain.station.");
        }
        System.out.printf(
                "Passenger %s %s changed the line from domain.station %s to domain.station %s.%n",
                passenger.getFirstname(), passenger.getSurname(),
                this.getName(), targetStation.getName()
        );
        return targetStation;
    }

    private void setTransferStations(Station[] stations){
        if (stations.length == 0){
            throw new IllegalArgumentException("No domain.station to add as interchange domain.station");
        }
        for (Station station : stations){
            addTransferStation(station);
        }
    }

    public Station changeStation(Passenger passenger, Station targetStation) {
        return changeLine(passenger, targetStation);
    }
}
