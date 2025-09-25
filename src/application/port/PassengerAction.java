package application.port;

import domain.people.passenger.Passenger;
import domain.station.Station;

@FunctionalInterface
public interface PassengerAction {

    void execute(Passenger passenger, Station station);
}
