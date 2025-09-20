package application.service.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public final class PaymentReceipt {
    private final String id;
    private final TransactionType type;
    private final BigDecimal amount;
    private final PaymentMethod method;
    private final LocalDateTime timestamp;

    public PaymentReceipt(String id, TransactionType type, BigDecimal amount,
                          PaymentMethod method, LocalDateTime timestamp) {
        this.id = Objects.requireNonNull(id);
        this.type = Objects.requireNonNull(type);
        this.amount = Objects.requireNonNull(amount);
        this.method = Objects.requireNonNull(method);
        this.timestamp = Objects.requireNonNull(timestamp);
    }

    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
