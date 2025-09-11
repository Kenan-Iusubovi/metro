import application.port.BookingService;
import application.service.BookingServiceImpl;
import application.service.payment.PaymentMethod;
import application.service.payment.PaymentServiceImpl;
import domain.people.employee.Driver;
import domain.people.employee.Mechanic;
import domain.people.passenger.Passenger;
import domain.people.passenger.PassengerCategory;
import domain.route.Line;
import domain.route.Schedule;
import domain.station.InterchangeStation;
import domain.station.Station;
import domain.station.Turnstile;
import domain.system.Metro;
import domain.ticket.Ticket;
import domain.train.Carriage;
import domain.train.CarriageStatus;
import domain.train.Train;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        Metro tbilisiMetro = new Metro("Tbilisi", LocalDate.now());
        tbilisiMetro.setPaymentService(new PaymentServiceImpl());
        tbilisiMetro.setServiceStartAt(LocalTime.of(8,30));
        tbilisiMetro.setServiceEndAt(LocalTime.of(11,59));

        BookingService bookingService = new BookingServiceImpl();

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

        Turnstile[] stationSquare2Turnstiles = {
                new Turnstile("SSQ2-T1", true),
                new Turnstile("SSQ2-T2", true),
                new Turnstile("SSQ2-T3", false)
        };

        Turnstile[] tsereteliTurnstiles = {
                new Turnstile("TSE-T1", true),
                new Turnstile("TSE-T2", false)
        };

        Turnstile[] technicalUniversityTurnstiles = {
                new Turnstile("TUN-T1", true),
                new Turnstile("TUN-T2", true)
        };

        Turnstile[] medicalUniversityTurnstiles = {
                new Turnstile("MED-T1", true),
                new Turnstile("MED-T2", false)
        };

        Turnstile[] delisiTurnstiles = {
                new Turnstile("DEL-T1", true),
                new Turnstile("DEL-T2", false)
        };

        Turnstile[] vazhaPshavelaTurnstiles = {
                new Turnstile("VAZ-T1", true),
                new Turnstile("VAZ-T2", true),
                new Turnstile("VAZ-T3", false)
        };

        Turnstile[] stateUniversityTurnstiles = {
                new Turnstile("UNI-T1", true),
                new Turnstile("UNI-T2", false)
        };

        Station[] saburtaloStations = {
                new InterchangeStation("Station Square2", 2001L, (byte) 2,
                        LocalDate.of(1979, 1, 1), stationSquare2Turnstiles),
                new Station("Tsereteli",              2002L, (byte) 2,
                        LocalDate.of(1979, 1, 1), tsereteliTurnstiles),
                new Station("Technical University",   2003L, (byte) 2,
                        LocalDate.of(1979, 1, 1), technicalUniversityTurnstiles),
                new Station("Medical University",     2004L, (byte) 2,
                        LocalDate.of(2004, 1, 1), medicalUniversityTurnstiles),
                new Station("Delisi",                 2005L, (byte) 2,
                        LocalDate.of(1979, 1, 1), delisiTurnstiles),
                new Station("Vazha-Pshavela",         2006L, (byte) 2,
                        LocalDate.of(2000, 1, 1), vazhaPshavelaTurnstiles),
                new Station("State University",       2007L, (byte) 2,
                        LocalDate.of(2017, 1, 1), stateUniversityTurnstiles)
        };



        Line saburtaloLine = new Line(
                7842, "Saburtalo", "GREEN", saburtaloStations
        );

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
                new InterchangeStation("Station Square1",    1001L, (byte) 2,
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

        InterchangeStation stationSquare1 = (InterchangeStation) akhmeteliVarketiliStations[7];
        InterchangeStation stationSquare2 = (InterchangeStation) saburtaloStations[0];

        stationSquare1.addTransferStation(stationSquare2);
        stationSquare2.addTransferStation(stationSquare1);

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

        Driver driver = new Driver("Levan", "Kiknavelidze",
                "DRV-1234", 7);

        Train tbilisiTrain1 = new Train("TB1", carriages);

        tbilisiTrain1.assignDriver(driver);
        driver.assignTrain(tbilisiTrain1);

        akhmeteliVarketiliLine.addTrain(tbilisiTrain1);

        Schedule schedule = Schedule.ScheudeleGenerator.fromTrainCount(
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
                akhmeteliVarketiliLine.getStations()[10]
        );

        Mechanic mechanic = new Mechanic("Jason", "Stethem",
                "SCJ-0569",25, tbilisiTrain1);

        mechanic.startWorking();
        mechanic.stopWorking();

        System.out.println("Thank you for using our metro system hope its " +
                "working well Bogdan ;) :)");
    }
}
