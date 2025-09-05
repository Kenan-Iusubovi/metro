package system;

import route.Line;
import station.Station;
import utils.ArrayUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Metro {

    private static long idCounter = 0;
    public static final String SYSTEM_VENDOR = "Metro network";
    private Long id;
    private String city;
    private LocalDate launchedOn;
    private LocalDateTime createdAt;
    private Line[] lines;

    public Metro(String city) {
        this.id = ++idCounter;
        setCity(city);
        this.createdAt = LocalDateTime.now();
        this.lines = new Line[0];
    }

    public static void greet() {
        System.out.println("Welcome to " + SYSTEM_VENDOR);
    }

    public Station findStationByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name of Station can't be null or empty.");
        }
        Station[] all = getStations();
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && name.equalsIgnoreCase(all[i].getName())) {
                return all[i];
            }
        }
        return null;
    }

    public Station findStationByCode(long code) {
        Station[] all = getStations();
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && all[i].getCode() == code) {
                return all[i];
            }
        }
        return null;
    }

    public void openAllStations() {
        Station[] all = getStations();
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null) {
                all[i].openAll();
            }
        }
    }

    public void closeAllStations() {
        Station[] all = getStations();
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null) {
                all[i].closeAll();
            }
        }
    }

    public void addLine(Line line) {
        if (line == null) {
            throw new IllegalArgumentException("Line must not be null");
        }
        this.lines = (Line[]) ArrayUtils.add(this.lines, line);
    }

    public void removeLine(Line line) {
        this.lines = (Line[]) ArrayUtils.delete(this.lines, line);
    }

    public Station[] getStations() {
        if (lines == null || lines.length == 0) {
            return new Station[0];
        }
        int total = 0;
        for (int i = 0; i < lines.length; i++) {
            Station[] s = lines[i] != null ? lines[i].getStations() : null;
            if (s != null) {
                total += s.length;
            }
        }
        Station[] result = new Station[total];
        int idx = 0;
        for (int i = 0; i < lines.length; i++) {
            Station[] s = lines[i] != null ? lines[i].getStations() : null;
            if (s != null) {
                for (int j = 0; j < s.length; j++) {
                    result[idx++] = s[j];
                }
            }
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City is required");
        }
        this.city = city;
    }

    public LocalDate getLaunchedOn() {
        return launchedOn;
    }

    public void setLaunchedOn(LocalDate launchedOn) {
        if (launchedOn == null) {
            throw new IllegalArgumentException("Launch date is required");
        }
        this.launchedOn = launchedOn;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("Creation time is required");
        }
        this.createdAt = createdAt;
    }

    public Line[] getLines() {
        return lines;
    }
}
