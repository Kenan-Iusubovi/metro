package station;

import utils.ArrayUtils;

import java.time.LocalDate;


public class Station {

    private static long idCounter = 0;

    private long id;

    private String name;

    private long code;

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

    public void addTurnstile(Turnstile t) {

        Object[] tmp = ArrayUtils.add(this.turnstiles, t);
        Turnstile[] out = new Turnstile[tmp.length];
        for (int i = 0; i < tmp.length; i++) out[i] = (Turnstile) tmp[i];
        this.turnstiles = out;
    }

    public void openAll() {
        for (int i = 0; i < turnstiles.length; i++) if (turnstiles[i] != null) turnstiles[i].open();
    }

    public void closeAll() {
        for (int i = 0; i < turnstiles.length; i++) if (turnstiles[i] != null) turnstiles[i].close();
    }

    public void activateAll() {
        for (int i = 0; i < turnstiles.length; i++) if (turnstiles[i] != null) turnstiles[i].activate();
    }

    public void deactivateAll() {
        for (int i = 0; i < turnstiles.length; i++) if (turnstiles[i] != null) turnstiles[i].deactivate();
    }

    public long getId() { return id; }
    public void setId(long id) { if (id <= 0) throw new IllegalArgumentException("id"); this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name"); this.name = name; }

    public long getCode() { return code; }
    public void setCode(long code) { if (code <= 0) throw new IllegalArgumentException("code"); this.code = code; }

    public boolean isAccessible() { return accessible; }
    public void setAccessible(boolean accessible) { this.accessible = accessible; }

    public byte getPlatformCount() { return platformCount; }
    public void setPlatformCount(byte platformCount) { if (platformCount <= 0) throw new IllegalArgumentException("platformCount"); this.platformCount = platformCount; }

    public LocalDate getBuiltOn() { return builtOn; }
    public void setBuiltOn(LocalDate builtOn) { if (builtOn == null) throw new IllegalArgumentException("builtOn"); this.builtOn = builtOn; }

    public Turnstile[] getTurnstiles() { return turnstiles; }
    public void setTurnstiles(Turnstile[] turnstiles) { if (turnstiles == null) throw new IllegalArgumentException("turnstiles"); this.turnstiles = turnstiles; }
}
