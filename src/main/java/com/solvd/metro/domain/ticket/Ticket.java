package com.solvd.metro.domain.ticket;

import com.solvd.metro.application.exception.InvalidTicketException;
import com.solvd.metro.application.service.payment.PaymentMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {

    private static long idCounter = 0;
    private static final Logger logger = LogManager.getLogger(Ticket.class);

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
            logger.error("Unfortunately your ticket status is {}",
                    this.getStatus().getDetailedStatus());
            throw new InvalidTicketException("Unfortunately your ticket status is "
                    + this.getStatus().getDetailedStatus());
        }
        this.status = TicketStatus.USED;
        return true;
    }

    public void cancel() {
        if (status != TicketStatus.ACTIVE) {
            logger.error("The ticket should be Active to Cancel it! Current status: {}"
                    , status.getDetailedStatus());
            throw new InvalidTicketException("The ticket should be " +
                    "Active to Cancel it! Current status: " + status.getDetailedStatus());
        }
        this.status = TicketStatus.CANCELLED;
        logger.info("Ticket with number {} was successfully canceled.", this.id);
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
            logger.error("Payment method can't be null.");
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