package route;

import station.Station;

import java.util.List;

public class Route {

    private static long idCounter = 0;

    private long id;

    private Station origin;

    private Station destination;

    private List<Station> path;


    public Route(Station origin, Station destination, List<Station> path) {

        this.id = ++idCounter;
        this.origin = origin;
        this.destination = destination;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public List<Station> getPath() {
        return path;
    }

    public void setPath(List<Station> path) {
        this.path = path;
    }
}
