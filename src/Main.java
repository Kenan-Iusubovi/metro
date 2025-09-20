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
import utils.MyDoublyLinkedList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Metro tbilisiMetro = new Metro("Tbilisi", LocalDate.now());
        tbilisiMetro.setPaymentService(new PaymentServiceImpl());
        tbilisiMetro.setServiceStartAt(LocalTime.of(8, 30));
        tbilisiMetro.setServiceEndAt(LocalTime.of(23, 59));

        BookingService bookingService = new BookingServiceImpl();

        Set<Turnstile> akhmeteliTurnstiles = Set.of(
                new Turnstile("AKH-T1", true),
                new Turnstile("AKH-T2", true),
                new Turnstile("AKH-T3", false)
        );

        Set<Turnstile> sarajishviliTurnstiles = Set.of(
                new Turnstile("SAR-T1", true),
                new Turnstile("SAR-T2", false)
        );

        Set<Turnstile> ghrmagheleTurnstiles = Set.of(
                new Turnstile("GHR-T1", true)
        );

        Set<Turnstile> didubeTurnstiles = Set.of(
                new Turnstile("DID-T1", true),
                new Turnstile("DID-T2", true),
                new Turnstile("DID-T3", false),
                new Turnstile("DID-T4", false)
        );

        Set<Turnstile> gotsiridzeTurnstiles = Set.of(
                new Turnstile("GOT-T1", true),
                new Turnstile("GOT-T2", false)
        );

        Set<Turnstile> nadzaladeviTurnstiles = Set.of(
                new Turnstile("NAD-T1", true),
                new Turnstile("NAD-T2", false)
        );

        Set<Turnstile> stationSquareTurnstiles = Set.of(
                new Turnstile("SSQ-T1", true),
                new Turnstile("SSQ-T2", true),
                new Turnstile("SSQ-T3", false),
                new Turnstile("SSQ-T4", false),
                new Turnstile("SSQ-T5", true)
        );

        Set<Turnstile> marjanishviliTurnstiles = Set.of(
                new Turnstile("MAR-T1", true),
                new Turnstile("MAR-T2", false)
        );

        Set<Turnstile> rustaveliTurnstiles = Set.of(
                new Turnstile("RUS-T1", true),
                new Turnstile("RUS-T2", false)
        );

        Set<Turnstile> freedomSquareTurnstiles = Set.of(
                new Turnstile("FRS-T1", true),
                new Turnstile("FRS-T2", true),
                new Turnstile("FRS-T3", false)
        );

        Set<Turnstile> avlabariTurnstiles = Set.of(
                new Turnstile("AVL-T1", true),
                new Turnstile("AVL-T2", false)
        );

        Set<Turnstile> samgoriTurnstiles = Set.of(
                new Turnstile("SAM-T1", true),
                new Turnstile("SAM-T2", false)
        );

        Set<Turnstile> isaniTurnstiles = Set.of(
                new Turnstile("ISA-T1", true)
        );

        Set<Turnstile> varketiliTurnstiles = Set.of(
                new Turnstile("VAR-T1", true),
                new Turnstile("VAR-T2", true),
                new Turnstile("VAR-T3", false),
                new Turnstile("VAR-T4", false)
        );

        Set<Turnstile> guramishviliTurnstiles = Set.of(
                new Turnstile("GUR-T1", true),
                new Turnstile("GUR-T2", false)
        );

        Set<Turnstile> aragveli300Turnstiles = Set.of(
                new Turnstile("ARA-T1", true),
                new Turnstile("ARA-T2", true),
                new Turnstile("ARA-T3", false)
        );

        Set<Turnstile> stationSquare2Turnstiles = Set.of(
                new Turnstile("SSQ2-T1", true),
                new Turnstile("SSQ2-T2", true),
                new Turnstile("SSQ2-T3", false)
        );

        Set<Turnstile> tsereteliTurnstiles = Set.of(
                new Turnstile("TSE-T1", true),
                new Turnstile("TSE-T2", false)
        );

        Set<Turnstile> technicalUniversityTurnstiles = Set.of(
                new Turnstile("TUN-T1", true),
                new Turnstile("TUN-T2", true)
        );

        Set<Turnstile> medicalUniversityTurnstiles = Set.of(
                new Turnstile("MED-T1", true),
                new Turnstile("MED-T2", false)
        );

        Set<Turnstile> delisiTurnstiles = Set.of(
                new Turnstile("DEL-T1", true),
                new Turnstile("DEL-T2", false)
        );

        Set<Turnstile> vazhaPshavelaTurnstiles = Set.of(
                new Turnstile("VAZ-T1", true),
                new Turnstile("VAZ-T2", true),
                new Turnstile("VAZ-T3", false)
        );

        Set<Turnstile> stateUniversityTurnstiles = Set.of(
                new Turnstile("UNI-T1", true),
                new Turnstile("UNI-T2", false)
        );

        List<Station> saburtaloStations = Arrays.asList(
                new InterchangeStation("Station Square2", 2001L, (byte) 2,
                        LocalDate.of(1979, 1, 1),
                        stationSquare2Turnstiles),
                new Station("Tsereteli", 2002L, (byte) 2,
                        LocalDate.of(1979, 1, 1),
                        tsereteliTurnstiles),
                new Station("Technical University", 2003L, (byte) 2,
                        LocalDate.of(1979, 1, 1),
                        technicalUniversityTurnstiles),
                new Station("Medical University", 2004L, (byte) 2,
                        LocalDate.of(2004, 1, 1),
                        medicalUniversityTurnstiles),
                new Station("Delisi", 2005L, (byte) 2,
                        LocalDate.of(1979, 1, 1),
                        delisiTurnstiles),
                new Station("Vazha-Pshavela", 2006L, (byte) 2,
                        LocalDate.of(2000, 1, 1),
                        vazhaPshavelaTurnstiles),
                new Station("State University", 2007L, (byte) 2,
                        LocalDate.of(2017, 1, 1),
                        stateUniversityTurnstiles)
        );

        Line saburtaloLine = new Line(
                7842, "Saburtalo", "GREEN", saburtaloStations
        );

        List<Station> akhmeteliVarketiliStations = Arrays.asList(
                new Station("Akhmeteli Theatre", 1000L, (byte) 2,
                        LocalDate.of(1989, 1, 1), akhmeteliTurnstiles),
                new Station("Sarajishvili", 1008L, (byte) 2,
                        LocalDate.of(1985, 1, 1), sarajishviliTurnstiles),
                new Station("Guramishvili", 1017L, (byte) 2,
                        LocalDate.of(1985, 1, 1), guramishviliTurnstiles),
                new Station("Ghrmaghele", 1009L, (byte) 2,
                        LocalDate.of(1985, 1, 1), ghrmagheleTurnstiles),
                new Station("Didube", 1007L, (byte) 2,
                        LocalDate.of(1966, 1, 1), didubeTurnstiles),
                new Station("Gotsiridze", 1010L, (byte) 2,
                        LocalDate.of(1966, 1, 1), gotsiridzeTurnstiles),
                new Station("Nadzaladevi", 1011L, (byte) 2,
                        LocalDate.of(1966, 1, 1), nadzaladeviTurnstiles),
                new InterchangeStation("Station Square1", 1001L, (byte) 2,
                        LocalDate.of(1975, 1, 1), stationSquareTurnstiles),
                new Station("Marjanishvili", 1002L, (byte) 2,
                        LocalDate.of(1966, 1, 1), marjanishviliTurnstiles),
                new Station("Rustaveli", 1003L, (byte) 2,
                        LocalDate.of(1966, 1, 1), rustaveliTurnstiles),
                new Station("Liberty Square", 1012L, (byte) 2,
                        LocalDate.of(1967, 1, 1), freedomSquareTurnstiles),
                new Station("Avlabari", 1013L, (byte) 2,
                        LocalDate.of(1967, 1, 1), avlabariTurnstiles),
                new Station("300 Aragveli", 1018L, (byte) 2,
                        LocalDate.of(1967, 1, 1), aragveli300Turnstiles),
                new Station("Isani", 1015L, (byte) 2,
                        LocalDate.of(1971, 1, 1), isaniTurnstiles),
                new Station("Samgori", 1014L, (byte) 2,
                        LocalDate.of(1971, 1, 1), samgoriTurnstiles),
                new Station("Varketili", 1016L, (byte) 2,
                        LocalDate.of(1981, 1, 1), varketiliTurnstiles)
        );


        Line akhmeteliVarketiliLine = new Line(
                5698, "Akhmeteli-Varketili", "RED", akhmeteliVarketiliStations
        );

        InterchangeStation stationSquare1 = (InterchangeStation) akhmeteliVarketiliStations.get(7);
        InterchangeStation stationSquare2 = (InterchangeStation) saburtaloStations.get(0);

        stationSquare1.addTransferStation(stationSquare2);
        stationSquare2.addTransferStation(stationSquare1);

        MyDoublyLinkedList<Carriage> carriages = MyDoublyLinkedList.of(
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
        );

        Driver driver = new Driver("Levan", "Kiknavelidze",
                "DRV-1234", 7);

        Train tbilisiTrain1 = new Train("TB1", carriages);

        tbilisiTrain1.assignDriver(driver);
        driver.assignTrain(tbilisiTrain1);

        akhmeteliVarketiliLine.addTrain(tbilisiTrain1);

        Schedule schedule = Schedule.ScheduleGenerator.generateDailySchedule(
                akhmeteliVarketiliLine, tbilisiMetro, 10, 15
        );
        tbilisiMetro.addSchedule(schedule);

        Passenger ninoPassenger = new Passenger("Nino", "Beridze",
                "nino@example.com", "+995555555", PassengerCategory.ADULT);

        System.out.println("Tbilisi Metro system created successfully!");

        Ticket ticket = bookingService.book(tbilisiMetro, ninoPassenger, PaymentMethod.METRO_CARD);

        tbilisiMetro.enterMetro(
                ninoPassenger, ticket,
                akhmeteliVarketiliLine.getStations().get(0),
                akhmeteliVarketiliLine.getStations().get(10)
        );

        Mechanic mechanic = new Mechanic("Jason", "Stethem",
                "SCJ-0569", 25, tbilisiTrain1);

        mechanic.startWorking();
        mechanic.stopWorking();

        System.out.println("Thank you for using our metro system hope its " +
                "working well Bogdan ;) :)");
    }
}
