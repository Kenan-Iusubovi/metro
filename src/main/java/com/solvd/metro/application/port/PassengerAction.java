package com.solvd.metro.application.port;

import com.solvd.metro.domain.people.passenger.Passenger;
import com.solvd.metro.domain.station.Station;

@FunctionalInterface
public interface PassengerAction {

    void execute(Passenger passenger, Station station);
}
