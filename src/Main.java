import payment.PaymentMethodE;
import payment.PaymentService;
import people.passenger.Passenger;
import people.passenger.PassengerCategoryE;
import people.worker.Worker;
import people.worker.WorkerProfessionE;
import route.Line;
import route.Route;
import route.Schedule;
import service.BookingService;
import station.Station;
import station.Turnstile;
import system.Metro;
import train.Carriage;
import train.Train;
import train.TrainMaintenanceRecord;
import train.TrainStatusE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {
        Metro.greet();

        Station stationSquare = new Station("Station Square", 2001, true,
                (byte) 4, LocalDate.of(1966, 1, 11));

        Station rustaveli = new Station("Rustaveli", 2002, true,
                (byte) 2, LocalDate.of(1966, 1, 11));

        Station libertySquare = new Station("Liberty Square", 2003, true,
                (byte) 2, LocalDate.of(1967, 11, 6));

        Station didube = new Station("Didube", 2004, true,
                (byte) 2, LocalDate.of(1966, 1, 11));

        Station[] tbilisiStations = new Station[]{stationSquare, rustaveli, libertySquare, didube};

        stationSquare.addTurnstile(new Turnstile("TB-T1", true, false));
        stationSquare.addTurnstile(new Turnstile("TB-T2", true, false));
        rustaveli.addTurnstile(new Turnstile("TB-T3", true, false));
        libertySquare.addTurnstile(new Turnstile("TB-T4", true, false));

        Carriage standardCarriage = new Carriage("Standard", (byte) 2,
                (short) 2018, 120, true);

        Train tbilisiTrain = new Train("TB-01", 300, 90.0,
                true, TrainStatusE.ACTIVE, (byte) 24);

        tbilisiTrain.addCarriage(standardCarriage);
        Train[] tbilisiTrains = new Train[]{tbilisiTrain};

        Line akhmeteliVarketiliLine = new Line(1L, "Akhmeteli-Varketili", "RED");

        for (Station station : tbilisiStations) {
            akhmeteliVarketiliLine.addStation(station);
        }

        for (Train train : tbilisiTrains) {
            akhmeteliVarketiliLine.addTrain(train);
        }

        Line[] tbilisiLines = new Line[]{akhmeteliVarketiliLine};

        Route tbilisiRoute = new Route(new Station[]{stationSquare, rustaveli, libertySquare, didube});
        Route[] tbilisiRoutes = new Route[]{tbilisiRoute};

        LocalTime[][] tbilisiScheduleTimes = new LocalTime[][]{
                new LocalTime[]{LocalTime.of(8, 0),
                        LocalTime.of(9, 0), LocalTime.of(10, 0)},

                new LocalTime[]{LocalTime.of(8, 5),
                        LocalTime.of(9, 5), LocalTime.of(10, 5)},

                new LocalTime[]{LocalTime.of(8, 10),
                        LocalTime.of(9, 10), LocalTime.of(10, 10)},

                new LocalTime[]{LocalTime.of(8, 15),
                        LocalTime.of(9, 15), LocalTime.of(10, 15)}
        };

        Schedule tbilisiSchedule = new Schedule(akhmeteliVarketiliLine, tbilisiScheduleTimes);
        Schedule[] tbilisiSchedules = new Schedule[]{tbilisiSchedule};

        Passenger ninoPassenger = new Passenger("Nino", "Beridze",
                "nino@example.com", "+995555000001", PassengerCategoryE.ADULT);

        Worker driverGiorgi = new Worker("Giorgi", "Kalandadze",
                "DRV-TB-001", 6, WorkerProfessionE.DRIVER, 'A');

        Passenger[] tbilisiPassengers = new Passenger[]{ninoPassenger};
        Worker[] tbilisiWorkers = new Worker[]{driverGiorgi};

        Turnstile[] additionalTurnstiles = new Turnstile[]{
                new Turnstile("TB-EXT1", true, false),
                new Turnstile("TB-EXT2", true, false)
        };

        TrainMaintenanceRecord[] maintenanceRecords = new TrainMaintenanceRecord[]{
                new TrainMaintenanceRecord(tbilisiTrain, LocalDateTime.now(),
                        "Brake system check", false, 2.0f)
        };

        PaymentService tbilisiPaymentService = new PaymentService();

        Metro tbilisiMetro = new Metro(
                "Tbilisi",
                LocalDate.of(1966, 1, 11),
                LocalDateTime.now(),
                tbilisiStations, tbilisiTrains, tbilisiLines, tbilisiRoutes, tbilisiSchedules,
                tbilisiPassengers, tbilisiWorkers, additionalTurnstiles, maintenanceRecords, tbilisiPaymentService
        );

        BookingService bookingService = new BookingService();

        var ticket = bookingService.book(tbilisiMetro, ninoPassenger, 1L,
                stationSquare, didube, PaymentMethodE.METRO_CARD);

        stationSquare.openAll();
        stationSquare.getTurnstiles()[0].pass(ticket);

        System.out.println("Tbilisi Metro system created successfully!");
        System.out.println("Stations: " + tbilisiStations.length);
        System.out.println("Trains: " + tbilisiTrains.length);
        System.out.println("Tickets issued: " + ninoPassenger.getTickets().length);
    }
}