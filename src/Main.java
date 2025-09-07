import payment.PaymentMethod;
import payment.PaymentService;
import people.passenger.Passenger;
import people.passenger.PassengerCategory;
import people.worker.Worker;
import people.worker.WorkerProfession;
import route.Line;
import route.Schedule;
import service.BookingService;
import station.Station;
import station.Turnstile;
import system.Metro;
import ticket.Ticket;
import train.Carriage;
import train.Train;
import train.CarriageStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        Metro tbilisiMetro = new Metro("Tbilisi", LocalDate.now());
        tbilisiMetro.setPaymentService(new PaymentService());
        tbilisiMetro.setServiceStartAt(LocalTime.of(8,30));
        tbilisiMetro.setServiceEndAt(LocalTime.of(11,59));
        BookingService bookingService = new BookingService();

        Turnstile[] akhmeteliTurnstiles = {
                new Turnstile("AKH-T1", true),
                new Turnstile("AKH-T2", true),
                new Turnstile("AKH-T3", false)
        };

        Turnstile[] sarajishviliTurnstiles = {
                new Turnstile("SAR-T1", true),
                new Turnstile("SAR-T2", false)
        };

        Turnstile[] ghrmagheleTurnstiles = {
                new Turnstile("GHR-T1", true)
        };

        Turnstile[] didubeTurnstiles = {
                new Turnstile("DID-T1", true),
                new Turnstile("DID-T2", true),
                new Turnstile("DID-T3", false),
                new Turnstile("DID-T4", false)
        };

        Turnstile[] gotsiridzeTurnstiles = {
                new Turnstile("GOT-T1", true),
                new Turnstile("GOT-T2", false)
        };

        Turnstile[] nadzaladeviTurnstiles = {
                new Turnstile("NAD-T1", true),
                new Turnstile("NAD-T2", false)
        };

        Turnstile[] stationSquareTurnstiles = {
                new Turnstile("SSQ-T1", true),
                new Turnstile("SSQ-T2", true),
                new Turnstile("SSQ-T3", false),
                new Turnstile("SSQ-T4", false),
                new Turnstile("SSQ-T5", true)
        };

        Turnstile[] marjanishviliTurnstiles = {
                new Turnstile("MAR-T1", true),
                new Turnstile("MAR-T2", false)
        };

        Turnstile[] rustaveliTurnstiles = {
                new Turnstile("RUS-T1", true),
                new Turnstile("RUS-T2", false)
        };

        Turnstile[] freedomSquareTurnstiles = {
                new Turnstile("FRS-T1", true),
                new Turnstile("FRS-T2", true),
                new Turnstile("FRS-T3", false)
        };

        Turnstile[] avlabariTurnstiles = {
                new Turnstile("AVL-T1", true),
                new Turnstile("AVL-T2", false)
        };

        Turnstile[] samgoriTurnstiles = {
                new Turnstile("SAM-T1", true),
                new Turnstile("SAM-T2", false)
        };

        Turnstile[] isaniTurnstiles = {
                new Turnstile("ISA-T1", true)
        };

        Turnstile[] varketiliTurnstiles = {
                new Turnstile("VAR-T1", true),
                new Turnstile("VAR-T2", true),
                new Turnstile("VAR-T3", false),
                new Turnstile("VAR-T4", false)
        };

        Turnstile[] guramishviliTurnstiles = {
                new Turnstile("GUR-T1", true),
                new Turnstile("GUR-T2", false)
        };

        Turnstile[] aragveli300Turnstiles = {
                new Turnstile("ARA-T1", true),
                new Turnstile("ARA-T2", true),
                new Turnstile("ARA-T3", false)
        };

        Station[] akhmeteliVarketiliStations = {
                new Station("Akhmeteli Theatre", 1000L, (byte) 2,
                        LocalDate.of(1989, 1, 1), akhmeteliTurnstiles),
                new Station("Sarajishvili",      1008L, (byte) 2,
                        LocalDate.of(1985, 1, 1), sarajishviliTurnstiles),
                new Station("Guramishvili",      1017L, (byte) 2,
                        LocalDate.of(1985, 1, 1), guramishviliTurnstiles),
                new Station("Ghrmaghele",        1009L, (byte) 2,
                        LocalDate.of(1985, 1, 1), ghrmagheleTurnstiles),
                new Station("Didube",            1007L, (byte) 2,
                        LocalDate.of(1966, 1, 1), didubeTurnstiles),
                new Station("Gotsiridze",        1010L, (byte) 2,
                        LocalDate.of(1966, 1, 1), gotsiridzeTurnstiles),
                new Station("Nadzaladevi",       1011L, (byte) 2,
                        LocalDate.of(1966, 1, 1), nadzaladeviTurnstiles),
                new Station("Station Square",    1001L, (byte) 2,
                        LocalDate.of(1975, 1, 1), stationSquareTurnstiles),
                new Station("Marjanishvili",     1002L, (byte) 2,
                        LocalDate.of(1966, 1, 1), marjanishviliTurnstiles),
                new Station("Rustaveli",         1003L, (byte) 2,
                        LocalDate.of(1966, 1, 1), rustaveliTurnstiles),
                new Station("Liberty Square",    1012L, (byte) 2,
                        LocalDate.of(1967, 1, 1), freedomSquareTurnstiles),
                new Station("Avlabari",          1013L, (byte) 2,
                        LocalDate.of(1967, 1, 1), avlabariTurnstiles),
                new Station("300 Aragveli",      1018L, (byte) 2,
                        LocalDate.of(1967, 1, 1), aragveli300Turnstiles),
                new Station("Isani",             1015L, (byte) 2,
                        LocalDate.of(1971, 1, 1), isaniTurnstiles),
                new Station("Samgori",           1014L, (byte) 2,
                        LocalDate.of(1971, 1, 1), samgoriTurnstiles),
                new Station("Varketili",         1016L, (byte) 2,
                        LocalDate.of(1981, 1, 1), varketiliTurnstiles)
        };

        Line akhmeteliVarketiliLine = new Line(
                5698, "Akhmeteli-Varketili", "RED", akhmeteliVarketiliStations
        );

        Carriage[] carriages = {
                new Carriage((byte) 4, (short) 2010, 40, CarriageStatus.ACTIVE),
                new Carriage((byte) 4, (short) 2011, 42, CarriageStatus.ACTIVE),
                new Carriage((byte) 4, (short) 2012, 38, CarriageStatus.ACTIVE),
                new Carriage((byte) 3, (short) 2013, 36, CarriageStatus.ACTIVE),
                new Carriage((byte) 4, (short) 2014, 44, CarriageStatus.ACTIVE),
                new Carriage((byte) 4, (short) 2015, 40, CarriageStatus.ACTIVE),
                new Carriage((byte) 2, (short) 2016, 30, CarriageStatus.ACTIVE),
                new Carriage((byte) 4, (short) 2017, 45, CarriageStatus.ACTIVE),
                new Carriage((byte) 3, (short) 2018, 37, CarriageStatus.ACTIVE),
                new Carriage((byte) 4, (short) 2019, 50, CarriageStatus.ACTIVE),
                new Carriage((byte) 4, (short) 2020, 48, CarriageStatus.ACTIVE),
                new Carriage((byte) 4, (short) 2021, 52, CarriageStatus.ACTIVE)
        };

        Worker driver = new Worker("Levan", "Kiknavelidze",
                "DRV-1234", 7, WorkerProfession.DRIVER, 'A');
        Train tbilisiTrain1 = new Train("TB1", carriages);
        tbilisiTrain1.assignDriver(driver);
        akhmeteliVarketiliLine.addTrain(tbilisiTrain1);

        Schedule schedule = Schedule.Generator.fromTrainCount(
                akhmeteliVarketiliLine, tbilisiMetro,1,
                2, 2, 30, 5
        );
        tbilisiMetro.addSchedule(schedule);

        Passenger ninoPassenger = new Passenger("Nino", "Beridze",
                "nino@example.com", "+995555555", PassengerCategory.ADULT);

        System.out.println("Tbilisi Metro system created successfully!");

        Ticket ticket = bookingService.book(tbilisiMetro, ninoPassenger, PaymentMethod.METRO_CARD);

        tbilisiMetro.enterMetro(
                ninoPassenger, ticket,
                akhmeteliVarketiliLine.getStations()[0],
                akhmeteliVarketiliLine.getStations()[15]
        );


    }
}
