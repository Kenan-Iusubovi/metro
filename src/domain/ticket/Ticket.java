package domain.ticket;

import application.exception.InvalidTicketException;
import application.service.payment.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {

    private static long idCounter = 0;

    private Long id;
    private UUID code;
    private BigDecimal price;
    private LocalDateTime issuedAt;
    private PaymentMethod paymentMethod;
    private TicketStatus status;

    public Ticket(BigDecimal price, PaymentMethod paymentMethod) {
        this.id = ++idCounter;
        this.code = UUID.randomUUID();
        this.price = price;
        setPaymentMethod(paymentMethod);
        this.issuedAt = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }

    private boolean isValidForEntry() {
        return status.isValidForEntry();
    }

    public boolean useForEntry() {
        if (!isValidForEntry()) {
            throw new InvalidTicketException("Unfortunately your ticket status is "
                    + this.getStatus().getDetailedStatus());
        }
        this.status = TicketStatus.USED;
        return true;
    }

    public void cancel() {
        if (status != TicketStatus.ACTIVE) {
            throw new InvalidTicketException("The ticket should be " +
                    "Active to Cancel it! Current status: " + status.getDetailedStatus());
        }
        this.status = TicketStatus.CANCELLED;
        System.out.println("Ticket with number " + this.id + " was successfully canceled.");
    }

    public Long getId() {
        return id;
    }

    public UUID getCode() {
        return code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method can't be null.");
        }
        this.paymentMethod = paymentMethod;
    }

    public TicketStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket other)) return false;
        return code.equals(other.getCode());
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "Ticket{id=%d, code=%s, status=%s}".formatted(id, code, status);
    }
}