package ticket;

import payment.PaymentMethodE;
import station.Station;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ticket {

    private static long idCounter = 0;

    private long id;

    private long lineCode;

    private Station origin;

    private Station destination;

    private BigDecimal price;

    private LocalDateTime issuedAt;

    private PaymentMethodE paymentMethod;

    private TicketStatusE status;

    public Ticket(long lineCode, Station origin, Station destination,
                  BigDecimal price,
                  PaymentMethodE paymentMethod) {

        this.id = ++idCounter;
        setLineCode(lineCode);
        setOrigin(origin);
        setDestination(destination);
        setPrice(price);
        setPaymentMethod(paymentMethod);
        this.issuedAt = LocalDateTime.now();
        this.status = TicketStatusE.ACTIVE;
    }

    public boolean isValidForEntry() {

        return status == TicketStatusE.ACTIVE;
    }

    public boolean useForEntry() {

        if (!isValidForEntry()) return false;

        this.status = TicketStatusE.USED;
        return true;
    }

    public boolean cancel() {

        if (status != TicketStatusE.ACTIVE) {

            throw new IllegalArgumentException("The ticket should be " +
                    "Active to Cancel it!");
        }
        this.status = TicketStatusE.CANCELLED;
        return true;
    }

    public long getId() {
        return id;
    }


    public long getLineCode() {

        return lineCode;
    }

    public void setLineCode(long lineCode) {

        if (lineCode <= 0) throw new IllegalArgumentException("lineCode");
        this.lineCode = lineCode;
    }

    public Station getOrigin() {

        return origin;
    }

    public void setOrigin(Station origin) {

        if (origin == null) throw new IllegalArgumentException("origin");
        this.origin = origin;
    }

    public Station getDestination() {

        return destination;
    }

    public void setDestination(Station destination) {

        if (destination == null) throw new IllegalArgumentException("destination");
        this.destination = destination;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {

        if (price == null || price.signum() < 0) throw new IllegalArgumentException("price");
        this.price = price;
    }

    public LocalDateTime getIssuedAt() {

        return issuedAt;
    }

    public PaymentMethodE getPaymentMethod() {

        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodE paymentMethod) {

        if (paymentMethod == null) throw new IllegalArgumentException("paymentMethod");
        this.paymentMethod = paymentMethod;
    }

    public TicketStatusE getStatus() {

        return status;
    }

    public void setStatus(TicketStatusE status) {

        if (status == null) throw new IllegalArgumentException("status");
        this.status = status;
    }
}
