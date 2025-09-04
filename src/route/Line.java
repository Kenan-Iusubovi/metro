package route;

import station.Station;
import train.Train;

import java.util.List;

public class Line {

    private long id;

    private long code;

    private String name;

    private List<Station> stations;

    private List<Train> assignedTrains;

    private boolean isActive;

    private String color;
}
