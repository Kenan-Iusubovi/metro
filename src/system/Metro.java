package system;

import payment.PaymentService;
import people.passenger.Passenger;
import people.worker.Worker;
import route.Line;
import route.Route;
import route.Schedule;
import station.Station;
import station.Turnstile;
import train.Train;
import train.TrainMaintenanceRecord;
import utils.ArrayUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Metro {

    public static final String SYSTEM_VENDOR = "Metro network";

    private String city;

    private LocalDate launchedOn;

    private LocalDateTime createdAt;

    private Station[] stations;

    private Train[] trains;

    private Line[] lines;

    private Route[] routes;

    private Schedule[] schedules;

    private Passenger[] passengers;

    private Worker[] workers;

    private Turnstile[] turnstiles;

    private TrainMaintenanceRecord[] maintenanceRecords;

    private PaymentService paymentService;

    public Metro(String city, LocalDate launchedOn,
                 LocalDateTime createdAt, Station[] stations,
                 Train[] trains, Line[] lines, Route[] routes,
                 Schedule[] schedules, Passenger[] passengers,
                 Worker[] workers, Turnstile[] turnstiles,
                 TrainMaintenanceRecord[] maintenanceRecords,
                 PaymentService paymentService) {

        setCity(city);
        setLaunchedOn(launchedOn);
        setCreatedAt(createdAt);

        if (stations == null) throw new IllegalArgumentException("stations");
        this.stations = stations;

        if (trains == null) throw new IllegalArgumentException("trains");
        this.trains = trains;

        if (lines == null) throw new IllegalArgumentException("lines");
        this.lines = lines;

        if (routes == null) throw new IllegalArgumentException("routes");
        this.routes = routes;

        if (schedules == null) throw new IllegalArgumentException("schedules");
        this.schedules = schedules;

        if (passengers == null) throw new IllegalArgumentException("passengers");
        this.passengers = passengers;

        if (workers == null) throw new IllegalArgumentException("workers");
        this.workers = workers;

        if (turnstiles == null) throw new IllegalArgumentException("turnstiles");
        this.turnstiles = turnstiles;

        if (maintenanceRecords == null) throw new IllegalArgumentException("maintenanceRecords");
        this.maintenanceRecords = maintenanceRecords;

        setPaymentService(paymentService);
    }

    public static void greet() {

        System.out.println("Welcome to " + SYSTEM_VENDOR);
    }

    public Station findStationByName(String name) {

        if (name == null) return null;
        for (int i = 0; i < stations.length; i++)
            if (stations[i] != null && name.equalsIgnoreCase(stations[i].getName()))
                return stations[i];

        return null;
    }

    public Station findStationByCode(long code) {

        for (int i = 0; i < stations.length; i++)
            if (stations[i] != null && stations[i].getCode() == code)
                return stations[i];

        return null;
    }

    public void openAllStations() {
        for (int i = 0; i < stations.length; i++) if (stations[i] != null)
            stations[i].openAll();
    }

    public void closeAllStations() {
        for (int i = 0; i < stations.length; i++) if (stations[i] != null)
            stations[i].closeAll();
    }

    public void addStation(Station s) {
        this.stations = (Station[]) ArrayUtils.add(this.stations, s);
    }

    public void removeStation(Station s) {
        this.stations = (Station[]) ArrayUtils.delete(this.stations, s);
    }

    public void addTrain(Train t) {
        this.trains = (Train[]) ArrayUtils.add(this.trains, t);
    }

    public void removeTrain(Train t) {
        this.trains = (Train[]) ArrayUtils.delete(this.trains, t);
    }

    public void addLine(Line l) {
        this.lines = (Line[]) ArrayUtils.add(this.lines, l);
    }

    public void removeLine(Line l) {
        this.lines = (Line[]) ArrayUtils.delete(this.lines, l);
    }

    public void addRoute(Route r) {
        this.routes = (Route[]) ArrayUtils.add(this.routes, r);
    }

    public void removeRoute(Route r) {
        this.routes = (Route[]) ArrayUtils.delete(this.routes, r);
    }

    public void addSchedule(Schedule s) {
        this.schedules = (Schedule[]) ArrayUtils.add(this.schedules, s);
    }

    public void removeSchedule(Schedule s) {
        this.schedules = (Schedule[]) ArrayUtils.delete(this.schedules, s);
    }

    public void addPassenger(Passenger p) {
        this.passengers = (Passenger[]) ArrayUtils.add(this.passengers, p);
    }

    public void removePassenger(Passenger p) {
        this.passengers = (Passenger[]) ArrayUtils.delete(this.passengers, p);
    }

    public void addWorker(Worker w) {
        this.workers = (Worker[]) ArrayUtils.add(this.workers, w);
    }

    public void removeWorker(Worker w) {
        this.workers = (Worker[]) ArrayUtils.delete(this.workers, w);
    }

    public void addTurnstile(Turnstile t) {
        this.turnstiles = (Turnstile[]) ArrayUtils.add(this.turnstiles, t);
    }

    public void removeTurnstile(Turnstile t) {
        this.turnstiles = (Turnstile[]) ArrayUtils.delete(this.turnstiles, t);
    }

    public void addMaintenanceRecord(TrainMaintenanceRecord m) {
        this.maintenanceRecords = (TrainMaintenanceRecord[]) ArrayUtils.add(this.maintenanceRecords, m);
    }

    public void removeMaintenanceRecord(TrainMaintenanceRecord m) {
        this.maintenanceRecords = (TrainMaintenanceRecord[]) ArrayUtils.delete(this.maintenanceRecords, m);
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        if (city == null || city.isBlank())
            throw new IllegalArgumentException("city");

        this.city = city;
    }

    public LocalDate getLaunchedOn() {

        return launchedOn;
    }

    public void setLaunchedOn(LocalDate launchedOn) {

        if (launchedOn == null)
            throw new IllegalArgumentException("launchedOn");

        this.launchedOn = launchedOn;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {

        if (createdAt == null)
            throw new IllegalArgumentException("createdAt");

        this.createdAt = createdAt;
    }

    public Station[] getStations() {

        return stations;
    }

    public Train[] getTrains() {

        return trains;
    }

    public Line[] getLines() {

        return lines;
    }

    public Route[] getRoutes() {

        return routes;
    }

    public Schedule[] getSchedules() {

        return schedules;
    }

    public Passenger[] getPassengers() {

        return passengers;
    }

    public Worker[] getWorkers() {

        return workers;
    }

    public Turnstile[] getTurnstiles() {

        return turnstiles;
    }

    public TrainMaintenanceRecord[] getMaintenanceRecords() {

        return maintenanceRecords;
    }

    public PaymentService getPaymentService() {

        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {

        if (paymentService == null)
            throw new IllegalArgumentException("paymentService");

        this.paymentService = paymentService;
    }
}
