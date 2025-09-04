package route;

import station.Station;
import utils.ArrayUtils;

public class Route {

    private static long idCounter = 0;

    private long id;

    private Station[] path;

    public Route(Station[] path) {

        this.id = ++idCounter;
        setPath(path);
    }

    public long getId() {

        return id;
    }

    public Station[] getPath() {

        return path;
    }

    public void setPath(Station[] path) {

        if (path == null || path.length < 2)
            throw new IllegalArgumentException("path must contain at least origin and destination");

        for (int i = 0; i < path.length; i++) {
            if (path[i] == null)
                throw new IllegalArgumentException("path contains null at index " + i);
        }
        this.path = path;
    }

    public Station getOrigin() {

        return (path != null && path.length > 0) ? path[0] : null;
    }

    public void setOrigin(Station origin) {

        if (origin == null)
            throw new IllegalArgumentException("origin");

        if (path == null || path.length < 2)
            throw new IllegalStateException("path must be initialized and have at least 2 stations");

        path[0] = origin;
    }

    public Station getDestination() {

        if (path == null || path.length == 0) return null;

        int i = path.length - 1;
        while (i >= 0 && path[i] == null) i--;
        return (i >= 0) ? path[i] : null;
    }

    public void setDestination(Station destination) {

        if (destination == null)
            throw new IllegalArgumentException("destination");

        if (path == null || path.length < 2)
            throw new IllegalStateException("path must be initialized and have at least 2 stations");

        int i = path.length - 1;
        while (i >= 0 && path[i] == null) i--;
        if (i < 0) throw new IllegalStateException("path has no non-null stations to set as destination");

        path[i] = destination;
    }

    public void addToPath(Station station) {

        if (station == null)
            throw new IllegalArgumentException("station");

        this.path = (Station[]) ArrayUtils.add(this.path, station);
    }

    public void removeFromPath(Station station) {

        if (station == null)
            throw new IllegalArgumentException("station");

        this.path = (Station[]) ArrayUtils.delete(this.path, station);
    }
}
