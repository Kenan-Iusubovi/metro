package com.solvd.metro.domain.system;

import com.solvd.metro.application.exception.ScheduleException;
import com.solvd.metro.application.port.PassengerAction;
import com.solvd.metro.application.port.PaymentService;
import com.solvd.metro.application.service.FareCalculator;
import com.solvd.metro.domain.people.passenger.Passenger;
import com.solvd.metro.domain.route.Schedule;
import com.solvd.metro.domain.station.Station;
import com.solvd.metro.domain.train.Train;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return getStations().stream()
                .filter(station -> station.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new ScheduleException("No station with name " + name + " was find in schedule"));

    }

    public Station findStationByCode(long code) {
        return getStations().stream()
                .filter(station -> station.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new ScheduleException("No station with code " + code
                        + " was find in schedule"));
    }

    public void openAllStations() {
        getStations().forEach(Station::openAll);
    }

    public void closeAllStations() {
        getStations().forEach(Station::closeAll);
    }

    public List<Station> getStations() {
        if (schedules == null || schedules.isEmpty()) {
            throw new ScheduleException("Schedule has no stations");
        }

        return schedules.stream()
                .filter(schedule -> schedule.getLine() != null)
                .filter(schedule -> schedule.getLine().getStations() != null)
                .filter(schedule -> !schedule.getLine().getStations().isEmpty())
                .flatMap(schedule -> schedule.getLine().getStations().stream())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.isEmpty() ? null : list));
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

    public void enterMetro(Runnable enterStation, Runnable startDriverWork, Station onboardingStation,
                           Station destinationStation, Passenger passenger) {
        schedules.stream()
                .filter(schedule -> schedule.getLine().getStations()
                        .contains(onboardingStation)).findFirst().ifPresentOrElse(
                        schedule -> {
                            LocalTime departureTime = schedule.nextDepartureTime(onboardingStation,
                                    LocalTime.now());

                            if (departureTime == null) {
                                throw new RuntimeException("Couldn't find the " +
                                        "departure time near to enter time!");
                            }
                            Train train = schedule.getTrainByDepartureTime(onboardingStation,
                                    departureTime);

                            PassengerAction boardPassenger = (passenger1,
                                                              station1) -> {
                                train.board(passenger1);
                                System.out.printf("%s %s arrived at destination station %s%n",
                                        passenger1.getFirstname(), passenger1.getSurname(),
                                        station1.getName());
                            };

                            enterStation.run();
                            startDriverWork.run();
                            train.enterTheTrain(departureTime, passenger, schedule.getLine(), destinationStation, onboardingStation, boardPassenger);
                        },
                        () -> {
                            throw new RuntimeException("Schedule doesn't contains " +
                                    "the onboarding station!");
                        });
    }
}

