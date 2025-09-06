package system;

import route.Line;
import route.Schedule;
import station.Station;
import utils.ArrayUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Metro {

    private static long idCounter = 0;
    public static final String SYSTEM_VENDOR = "Metro network";
    private Long id;
    private String city;
    private LocalDateTime launchedOn;
    private LocalDate createdAt;
    private Schedule[] schedules;

    public Metro(String city, LocalDate createdAt) {
        this.id = ++idCounter;
        setCity(city);
        this.createdAt = createdAt;
        this.launchedOn = LocalDateTime.now();
        this.schedules = new Schedule[0];
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

        this.schedules = (Line[]) ArrayUtils.add(this.schedules, line);
    }

    public void removeLine(Line line) {
        this.schedules = (Line[]) ArrayUtils.delete(this.schedules, line);
    }

    public Station[] getStations() {
        if (schedules == null || schedules.length == 0) {
            return new Station[0];
        }
        int total = 0;
        for (int i = 0; i < schedules.length; i++) {
            Station[] s = schedules[i] != null ? schedules[i].getLine().getStations() : null;
            if (s != null) {
                total += s.length;
            }
        }
        Station[] result = new Station[total];
        int idx = 0;
        for (int i = 0; i < schedules.length; i++) {
            Station[] s = schedules[i] != null ? schedules[i].getLine().getStations() : null;
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

    public LocalDateTime getLaunchedOn() {
        return launchedOn;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("Creation time is required");
        }
        this.createdAt = createdAt;
    }

    public Schedule[] getSchedules() {
        return schedules;
    }
}
