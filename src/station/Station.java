package station;

import utils.ArrayUtils;

import java.time.LocalDate;


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

    public void openTurnstile(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code can't be null or empty.");
        }
        for (int i = 0; i < turnstiles.length; i++) {
            if (turnstiles[i] != null && code.equalsIgnoreCase(turnstiles[i].getCode())) {
                turnstiles[i].open();
                try {
                    wait(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                turnstiles[i].close();
                return;
            }
        }
        throw new IllegalArgumentException("Turnstile with code " + code + " not found");
    }

    public void activateAll() {
        for (int i = 0; i < turnstiles.length; i++) {
            if (turnstiles[i] != null) {
                turnstiles[i].activate();
            }
        }
    }

    public void deactivateAll() {
        for (int i = 0; i < turnstiles.length; i++) {
            if (turnstiles[i] != null) {
                turnstiles[i].deactivate();
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
        if (turnstile == null){
            throw new IllegalArgumentException("Turnstile can't be null.");
        }
        this.turnstiles = (Turnstile[]) ArrayUtils.add(this.turnstiles, turnstile);
    }

    public void removeTurnstile(Turnstile turnstile) {
        if (turnstile == null){
            throw new IllegalArgumentException("Turnstile can't be null.");
        }
        this.turnstiles = (Turnstile[]) ArrayUtils.delete(this.turnstiles, turnstile);
    }
}
