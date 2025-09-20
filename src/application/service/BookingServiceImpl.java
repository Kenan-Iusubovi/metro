package application.service;

import application.exception.MetroOperationException;
import application.exception.PaymentFailedException;
import application.port.*;
import application.service.payment.PaymentMethod;
import application.service.payment.PaymentSession;
import domain.people.passenger.Passenger;
import domain.system.Metro;
import domain.ticket.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.*;

public class BookingServiceImpl implements BookingService {

    private Set<Ticket> issuedTickets;
    private EmailService emailService;
    private FareCalculator fareCalculatorService;

    private final Predicate<Passenger> isEligibleForDiscount = passenger ->
            passenger.getCategory().name().equals("CHILD") ||
                    passenger.getCategory().name().equals("STUDENT") ||
                    passenger.getCategory().name().equals("SENIOR") ||
                    passenger.getCategory().name().equals("DISABLED");

    private final Function<Passenger, String> passengerToEmailBody = passenger ->
            "Passenger: " + passenger.getFirstname() + " " + passenger.getSurname() +
                    "\nCategory: " + passenger.getCategory().name();

    private final Consumer<String> bookingLogger = message ->
            System.out.println("[BOOKING LOG] " + LocalDateTime.now() + ": " + message);

    private final Supplier<String> bookingReferenceSupplier = () ->
            "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

    private final BiFunction<BigDecimal, String, BigDecimal> priceCalculator =
            (basePrice, categoryName) -> {
                switch (categoryName) {
                    case "CHILD": return basePrice.multiply(BigDecimal.valueOf(0.5));
                    case "STUDENT": return basePrice.multiply(BigDecimal.valueOf(0.75));
                    case "SENIOR": return basePrice.multiply(BigDecimal.valueOf(0.7));
                    case "DISABLED": return basePrice.multiply(BigDecimal.valueOf(0.6));
                    default: return basePrice;
                }
            };

    private final TicketValidator ticketValidator = (ticket, passenger) ->
            ticket != null && passenger != null;

    private final PassengerTransformer passengerInfoTransformer = passenger ->
            String.format("%s %s (%s)", passenger.getFirstname(), passenger.getSurname(),
                    passenger.getCategory().name());

    private final MetroOperation safeBookingOperation = () -> {
        if (Math.random() < 0.1) {
            throw new MetroOperationException("System temporarily unavailable");
        }
    };

    public BookingServiceImpl() {
        this.issuedTickets = new HashSet<>();
        this.emailService = new EmailServiceImpl();
        this.fareCalculatorService = new FareCalculator();
    }

    private void checkConditions(Metro metro, Passenger passenger) {
        if (metro == null) {
            throw new IllegalArgumentException("Metro can't be null.");
        }
        if (metro.getPaymentService() == null) {
            throw new IllegalArgumentException("Payment service can't be null.");
        }
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger can't be null");
        }
    }

    @Override
    public Ticket book(Metro metro, Passenger passenger, PaymentMethod method) {
        checkConditions(metro, passenger);

        bookingLogger.accept("Booking started for: " + passengerInfoTransformer.transform(passenger));

        BigDecimal price;
        if (isEligibleForDiscount.test(passenger)) {
            price = priceCalculator.apply(
                    fareCalculatorService.calculateTicketPrice(passenger.getCategory()),
                    passenger.getCategory().name()
            );
        } else {
            price = fareCalculatorService.calculateTicketPrice(passenger.getCategory());
        }

        try {
            safeBookingOperation.execute();
        } catch (MetroOperationException e) {
            throw new RuntimeException("Safety check failed: " + e.getMessage(), e);
        }

        try (PaymentSession session = new PaymentSession()) {
            session.processPayment(price, method);

            Ticket ticket = new Ticket(price, method);
            passenger.addTicket(ticket);
            this.issuedTickets.add(ticket);

            String passengerInfo = passengerToEmailBody.apply(passenger);
            emailService.sendTicketPurchaseSuccess(
                    passenger.getEmail(),
                    passenger.getFirstname() + " " + passenger.getSurname(),
                    ticket.getCode(),
                    price.toPlainString()
            );

            String bookingRef = bookingReferenceSupplier.get();
            bookingLogger.accept("Booking completed. Reference: " + bookingRef);

            return ticket;

        } catch (PaymentFailedException e) {
            throw new RuntimeException("Booking failed: " + e.getMessage(), e);
        } finally {
            System.out.println("Payment attempt finished, session cleaned up.");
        }
    }

    @Override
    public void cancelTicket(Passenger passenger, Ticket ticket) {
        if (!ticketValidator.validate(ticket, passenger)) {
            throw new IllegalArgumentException("Ticket or passenger can't be null or empty.");
        }
        this.issuedTickets.remove(ticket);
        passenger.removeTicket(ticket);
        bookingLogger.accept("Ticket cancelled: " + ticket.getCode());
    }

    public Set<Ticket> getIssuedTickets() {
        return issuedTickets;
    }

    public void setIssuedTickets(Set<Ticket> issuedTickets) {
        if (issuedTickets == null) {
            throw new IllegalArgumentException("Ticket can't be null.");
        }
        this.issuedTickets = issuedTickets;
    }
}