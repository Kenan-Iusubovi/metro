package system;

import payment.PaymentService;
import people.passenger.Passenger;
import route.Schedule;
import service.FareCalculator;
import station.Station;
import ticket.Ticket;
import train.Train;
import utils.ArrayUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Metro {

    private static long idCounter = 0;
    public static final String SYSTEM_VENDOR = "Metro network";

    private Long id;
    private String city;
    private LocalDateTime launchedOn;
    private LocalDate createdAt;
    private Schedule[] schedules;
    private PaymentService paymentService;
    private LocalTime serviceStartAt;
    private LocalTime serviceEndAt;

    public Metro(String city, LocalDate createdAt) {
        this.id = ++idCounter;
        setCity(city);
        this.createdAt = createdAt;
        this.launchedOn = LocalDateTime.now();
        this.schedules = new Schedule[0];
    }

    public Metro(String city, LocalDate createdAt, Schedule schedule) {
        this.id = ++idCounter;
        setCity(city);
        this.createdAt = createdAt;
        this.launchedOn = LocalDateTime.now();
        setSchedules(schedules);
    }

    public static void setUp() {
        System.out.println("Welcome to " + SYSTEM_VENDOR);
        FareCalculator.setBaseCost(2.50);
        FareCalculator.setChildDiscountPercentage(100);
        FareCalculator.setDisableDiscountPercentage(100);
        FareCalculator.setSeniorDiscountPercentage(75);
        FareCalculator.setStudentDiscountPercentage(50);
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

    public void addSchedule(Schedule schedule){
        if (schedule == null){
            throw new IllegalArgumentException("Schedule can't be null.");
        }
        this.schedules = (Schedule[]) ArrayUtils.add(this.schedules,schedule);
    }

    private void setSchedules(Schedule[] schedules){
        if (schedules == null){
            throw new IllegalArgumentException("Schedules can't be null.");
        }
        if (schedules.length < 1){
            throw new IllegalArgumentException("Add at least 1 schedule.");
        }
        this.schedules = new Schedule[0];
        for (Schedule schedule : schedules){
            addSchedule(schedule);
        }
    }

    public LocalTime getServiceEndAt() {
        return serviceEndAt;
    }

    public void setServiceEndAt(LocalTime serviceEndAt) {
        if (serviceEndAt == null){
            throw new IllegalArgumentException("Metro service start time can't be null.");
        }
        this.serviceEndAt = serviceEndAt;
    }

    public LocalTime getServiceStartAt() {
        return serviceStartAt;
    }

    public void setServiceStartAt(LocalTime serviceStartAt) {
        if (serviceStartAt == null){
            throw new IllegalArgumentException("Metro service start time can't be null.");
        }
        this.serviceStartAt = serviceStartAt;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void enterMetro(Passenger passenger, Ticket ticket,
                           Station onboardingStation, Station destinationStation){
        if (passenger == null){
            throw new IllegalArgumentException("Passenger can't be null and enter the station!");
        }
        if (ticket == null){
            throw new IllegalArgumentException("You can't enter without ticket. " +
                    "Ticket can.t be null.");
        }
        if (onboardingStation == null){
            throw new IllegalArgumentException("Onboarding station can't be null.");
        }
        if (destinationStation == null){
            throw new IllegalArgumentException("Destination station can't be null.");
        }
        LocalTime departureTime = null;

        for (Schedule schedule : schedules){
            station.Station[] stations = schedule.getLine().getStations();
            for (Station station : stations){
                if (station.equals(onboardingStation)){
                    departureTime = schedule.nextDepartureTime(station,LocalTime.now());
                    if (departureTime != null){
                        station.enterStation(passenger,ticket);

                        Train train = schedule.getTrainByDepartureTime(station,departureTime);
                        train.getDriver().startWorking();
                        Station destination = train.enterTheTrain(departureTime,passenger,
                                schedule.getLine(),destinationStation);

                        train.getDriver().stopWorking();
                        System.out.println(passenger.getFirstname() + passenger.getSurname() +
                                " arrived at destination station " + destination.getName());
                        System.out.println("Thank you for using our metro system hope its " +
                                "working well Bogdan ;) :)");
                        return;
                    }
                }
            }
        }
        if (departureTime == null){
            throw new RuntimeException("Couldn't find the departure time near to enter time!");
        }
    }
}
