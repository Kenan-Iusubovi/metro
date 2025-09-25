package domain.system;

import application.exception.ScheduleException;
import application.port.PaymentService;
import application.service.FareCalculator;
import domain.people.passenger.Passenger;
import domain.route.Schedule;
import domain.station.Station;
import domain.ticket.Ticket;
import domain.train.Train;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Metro {

    private static long idCounter = 0;
    public static final String SYSTEM_VENDOR = "Metro network";

    private Long id;
    private String city;
    private LocalDateTime launchedOn;
    private LocalDate createdAt;
    private List<Schedule> schedules;
    private PaymentService paymentService;
    private LocalTime serviceStartAt;
    private LocalTime serviceEndAt;

    public Metro(String city, LocalDate createdAt) {
        this.id = ++idCounter;
        setCity(city);
        this.createdAt = createdAt;
        this.launchedOn = LocalDateTime.now();
        this.schedules = new ArrayList<>();
    }

    public Metro(String city, LocalDate createdAt, List<Schedule> schedule) {
        this.id = ++idCounter;
        setCity(city);
        this.createdAt = createdAt;
        this.launchedOn = LocalDateTime.now();
        setSchedules(schedule);
    }

    public static void setUp() {
        System.out.println("Welcome to " + SYSTEM_VENDOR);
        FareCalculator.setBaseCost(2.50);
        FareCalculator.setChildDiscountPercentage(100);
        FareCalculator.setDisabledDiscountPercentage(100);
        FareCalculator.setSeniorDiscountPercentage(75);
        FareCalculator.setStudentDiscountPercentage(50);
    }

    public Station findStationByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name of Station can't be null or empty.");
        }
        List<Station> stations = getStations();
        for (Station station : stations) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        throw new ScheduleException("No station with name " + name + " was find in schedule");
    }

    public Station findStationByCode(long code) {
        List<Station> stations = getStations();
        for (Station station : stations) {
            if (station.getCode() == code) {
                return station;
            }
        }
        throw new ScheduleException("No station with code " + code + " was find in schedule");
    }

    public void openAllStations() {
        List<Station> stations = getStations();
        for (Station station : stations) {
            station.openAll();
        }
    }

    public void closeAllStations() {
        List<Station> stations = getStations();
        for (Station station : stations) {
            station.closeAll();
        }
    }

    public List<Station> getStations() {
        if (schedules == null || schedules.isEmpty()) {
            throw new ScheduleException("Schedule has no stations");
        }
        List<Station> stations = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getLine() != null && schedule.getLine().getStations() != null
                    && schedule.getLine().getStations().isEmpty()) {
                stations.addAll(schedule.getLine().getStations());
            }
            return stations;
        }
        return null;
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

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void addSchedule(Schedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("Schedule can't be null.");
        }
        this.schedules.add(schedule);
    }

    private void setSchedules(List<Schedule> schedules) {
        if (schedules == null) {
            throw new IllegalArgumentException("Schedules can't be null.");
        }
        if (schedules.isEmpty()) {
            throw new IllegalArgumentException("Add at least 1 schedule.");
        }
        this.schedules = schedules;
    }

    public LocalTime getServiceEndAt() {
        return serviceEndAt;
    }

    public void setServiceEndAt(LocalTime serviceEndAt) {
        if (serviceEndAt == null) {
            throw new IllegalArgumentException("Metro application.service start time can't be null.");
        }
        this.serviceEndAt = serviceEndAt;
    }

    public LocalTime getServiceStartAt() {
        return serviceStartAt;
    }

    public void setServiceStartAt(LocalTime serviceStartAt) {
        if (serviceStartAt == null) {
            throw new IllegalArgumentException("Metro application.service start time can't be null.");
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
                           Station onboardingStation, Station destinationStation) {
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger can't be null and enter the station!");
        }
        if (ticket == null) {
            throw new IllegalArgumentException("You can't enter without domain.ticket. " +
                    "Ticket can.t be null.");
        }
        if (onboardingStation == null) {
            throw new IllegalArgumentException("Onboarding station can't be null.");
        }
        if (destinationStation == null) {
            throw new IllegalArgumentException("Destination station can't be null.");
        }

        for (Schedule schedule : schedules) {
            List<Station> stations = schedule.getLine().getStations();

            for (Station station : stations) {

                if (station.equals(onboardingStation)) {

                    LocalTime departureTime = schedule.nextDepartureTime(station, LocalTime.now());
                    if (departureTime == null) {
                        throw new RuntimeException("Couldn't find the departure time near to enter time!");
                    }

                    Runnable enterStation = () -> station.enterStation(passenger, ticket);



                    Train train = schedule.getTrainByDepartureTime(station, departureTime);

                    Runnable startDriverWork = () -> train.getDriver().startWorking();

                    Runnable enterTheTrain = () -> train.enterTheTrain(departureTime, passenger,
                            schedule.getLine(), destinationStation, onboardingStation);

                    enterStation.run();
                    startDriverWork.run();
                    enterTheTrain.run();
                    return;
                }
            }
        }
    }
}

