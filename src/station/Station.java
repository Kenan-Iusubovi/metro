package station;

import java.util.List;

public class Station {

    private static long idCounter = 0;

    private long id;

    private long code;

    private String name;

    private List<Turnstile> turnstiles;

    public Station(long code, String name, List<Turnstile> turnstiles) {

        this.id = ++idCounter;
        this.code = code;
        this.name = name;
        this.turnstiles = turnstiles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Turnstile> getTurnstiles() {
        return turnstiles;
    }

    public void setTurnstiles(List<Turnstile> turnstiles) {
        this.turnstiles = turnstiles;
    }
}
