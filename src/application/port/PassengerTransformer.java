package application.port;

import domain.people.passenger.Passenger;

@FunctionalInterface
public interface PassengerTransformer {

    String transform(Passenger passenger);
}
