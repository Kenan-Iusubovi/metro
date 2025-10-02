package domain.station;

import application.exception.TurnstileUnavailableException;
import application.port.TicketValidator;
import domain.people.passenger.Passenger;
import domain.ticket.Ticket;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class Station {

    private static long idCounter = 0;

    private Long id;
    private String name;
    private Long code;
    private boolean accessible;
    private byte platformCount;
    private LocalDate builtOn;
    private Set<Turnstile> turnstiles;

    public Station(String name, long code, boolean accessible,
                   byte platformCount, LocalDate builtOn) {
        this.id = ++idCounter;
        setName(name);
        setCode(code);
        setAccessible(accessible);
        setPlatformCount(platformCount);
        setBuiltOn(builtOn);
        this.turnstiles = new HashSet<>();
    }

    public Station(String name, long code, byte platformCount, LocalDate builtOn) {
        this.id = ++idCounter;
        setName(name);
        setCode(code);
        setPlatformCount(platformCount);
        this.accessible = false;
        setBuiltOn(builtOn);
        this.turnstiles = new HashSet<>();
    }

    public Station(String name, long code, byte platformCount,
                   LocalDate builtOn, Set<Turnstile> turnstiles) {
        this.id = ++idCounter;
        setName(name);
        setCode(code);
        setPlatformCount(platformCount);
        this.accessible = false;
        setBuiltOn(builtOn);
        this.turnstiles = new HashSet<>();
        setTurnstiles(turnstiles);
    }

    public void openAll() {
        turnstiles.stream()
                .filter(Turnstile::isActive)
                .forEach(Turnstile::open);
    }

    public void closeAll() {
        turnstiles.stream()
                .filter(Turnstile::isActive)
                .forEach(Turnstile::close);
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

    public Set<Turnstile> getTurnstiles() {
        return turnstiles;
    }

    public void addTurnstile(Turnstile turnstile) {
        if (turnstile == null) {
            throw new IllegalArgumentException("Turnstile can't be null.");
        }
        this.turnstiles.add(turnstile);
    }

    private void setTurnstiles(Set<Turnstile> turnstiles) {
        if (turnstiles == null) {
            throw new IllegalArgumentException("Turnstiles can't be null");
        }
        if (turnstiles.isEmpty()) {
            throw new TurnstileUnavailableException("Add at least 1 turnstile.");
        }
        turnstiles.stream()
                .forEach(this::addTurnstile);
    }

    private boolean stationHasActiveTurnstile() {
        return turnstiles.stream()
                .anyMatch(Turnstile::isActive);
    }

    public void removeTurnstile(Turnstile turnstile) {
        if (turnstile == null) {
            throw new IllegalArgumentException("Turnstile can't be null.");
        }
        this.turnstiles.remove(turnstile);
    }

    public void enterStation(Passenger passenger, Ticket ticket) {
        System.out.printf("Passenger %s %s entered station %s%n",
                passenger.getFirstname(), passenger.getSurname(), this.getName());

        Turnstile activeTurnstile = turnstiles.stream()
                .filter(Turnstile::isActive)
                .findAny()
                .orElseThrow(() -> new RuntimeException("No active turnstile available"));

        TicketValidator ticketValidator = (passenger1, ticket1) ->
                passenger1 != null && ticket1 != null &&
                        !passenger1.getTickets().isEmpty() && passenger1.getTickets().contains(ticket1);

        activeTurnstile.pass(passenger, ticket, ticketValidator);

        System.out.printf("Passenger %s %s used turnstile %d%n",
                passenger.getFirstname(), passenger.getSurname(), activeTurnstile.getId());
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
