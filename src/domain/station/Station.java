package domain.station;

import application.exception.TurnstileUnavailableException;
import domain.people.passenger.Passenger;
import domain.ticket.Ticket;
import utils.ArrayUtils;

import java.time.LocalDate;
import java.util.Random;


public class Station {

    private static long idCounter = 0;

    private Long id;
    private String name;
    private Long code;
    private boolean accessible;
    private byte platformCount;
    private LocalDate builtOn;
    private Turnstile[] turnstiles;

    public Station(String name, long code, boolean accessible,
                   byte platformCount, LocalDate builtOn) {
        this.id = ++idCounter;
        setName(name);
        setCode(code);
        setAccessible(accessible);
        setPlatformCount(platformCount);
        setBuiltOn(builtOn);
        this.turnstiles = new Turnstile[0];
    }

    public Station(String name, long code, byte platformCount, LocalDate builtOn) {
        this.id = ++idCounter;
        setName(name);
        setCode(code);
        setPlatformCount(platformCount);
        this.accessible = false;
        setBuiltOn(builtOn);
    }

    public Station(String name, long code, byte platformCount,
                   LocalDate builtOn, Turnstile[] turnstiles) {
        this.id = ++idCounter;
        setName(name);
        setCode(code);
        setPlatformCount(platformCount);
        this.accessible = false;
        setBuiltOn(builtOn);
        setTurnstiles(turnstiles);
    }

    public void openAll() {
        for (int i = 0; i < turnstiles.length; i++) {
            if (turnstiles[i] != null) {
                turnstiles[i].open();
            }
        }
    }

    public void closeAll() {
        for (int i = 0; i < turnstiles.length; i++) {
            if (turnstiles[i] != null) {
                turnstiles[i].close();
            }
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name can't be null or empty.");
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        if (code <= 0) {
            throw new IllegalArgumentException("Code can't be negative.");
        }
        this.code = code;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public byte getPlatformCount() {
        return platformCount;
    }

    public void setPlatformCount(byte platformCount) {
        if (platformCount <= 0) {
            throw new IllegalArgumentException("Platform count can't be negative.");
        }
        this.platformCount = platformCount;
    }

    public LocalDate getBuiltOn() {
        return builtOn;
    }

    public void setBuiltOn(LocalDate builtOn) {
        if (builtOn == null) {
            throw new IllegalArgumentException("Built date can't be null.");
        }
        this.builtOn = builtOn;
    }

    public Turnstile[] getTurnstiles() {
        return turnstiles;
    }

    public void addTurnstile(Turnstile turnstile) {
        if (turnstile == null) {
            throw new IllegalArgumentException("Turnstile can't be null.");
        }
        this.turnstiles = (Turnstile[]) ArrayUtils.add(this.turnstiles, turnstile);
    }

    private void setTurnstiles(Turnstile[] turnstiles) {
        if (turnstiles == null) {
            throw new IllegalArgumentException("Turnstiles can't be null");
        }
        if (turnstiles.length < 1) {
            throw new TurnstileUnavailableException("Add at least 1 turnstile.");
        }
        this.turnstiles = new Turnstile[0];
        for (Turnstile turnstile : turnstiles) {
            addTurnstile(turnstile);
        }
    }

    private boolean stationHasActiveTurnstile() {
        for (Turnstile turnstile : turnstiles) {
            if (turnstile.isActive()) {
                return true;
            }
        }
        return false;
    }

    public void removeTurnstile(Turnstile turnstile) {
        if (turnstile == null) {
            throw new IllegalArgumentException("Turnstile can't be null.");
        }
        this.turnstiles = (Turnstile[]) ArrayUtils.delete(this.turnstiles, turnstile);
    }

    public void enterStation(Passenger passenger, Ticket ticket) {
        System.out.printf("Passenger %s %s entered domain.station %s%n",
                passenger.getFirstname(), passenger.getSurname(), this.getName());
        Random r = new Random();
        int randomTurnstile = r.nextInt(0, turnstiles.length);
        if (!stationHasActiveTurnstile()) {
            throw new RuntimeException("No active turnstile on this domain.station.");
        }
        while (!turnstiles[randomTurnstile].isActive()) {
            randomTurnstile = r.nextInt(0, turnstiles.length);
        }
        turnstiles[randomTurnstile].pass(ticket);
    }

    public void audit(String message) {
        System.out.printf("[AUDIT][%s][Station %s] %s%n",
                java.time.LocalDateTime.now(),
                getName(),
                message
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station other)) return false;
        return this.code == other.getCode();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(code);
    }

    @Override
    public String toString() {
        return "Station{name='%s', code=%d}".formatted(name, code);
    }
}
