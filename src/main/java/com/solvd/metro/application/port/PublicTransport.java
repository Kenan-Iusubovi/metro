package com.solvd.metro.application.port;

import com.solvd.metro.domain.people.employee.Driver;
import com.solvd.metro.domain.people.passenger.Passenger;

public interface PublicTransport {

    void board(Passenger passenger);

    void alight(Passenger passenger);

    Driver getDriver();

    void assignDriver(Driver driver);

    int getCapacity();

    int getDoorsAmount();
}
