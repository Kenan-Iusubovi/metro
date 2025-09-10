package application.port;

import domain.people.employee.Driver;
import domain.people.passenger.Passenger;

public interface PublicTransport {

    void board(Passenger passenger);
    void alight(Passenger passenger);
    Driver getDriver();
    void assignDriver(Driver driver);
    int getCapacity();
    int getDoorsAmount();
}
