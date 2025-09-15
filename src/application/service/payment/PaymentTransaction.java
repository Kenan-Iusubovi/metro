package application.service.payment;

import application.exception.PaymentFailedException;
import application.port.PaymentService;
import java.math.BigDecimal;

public final class PaymentTransaction implements AutoCloseable {
    private final PaymentService payments;
    private final BigDecimal amount;
    private final PaymentMethod method;
    private boolean committed;

    public PaymentTransaction(PaymentService payments, BigDecimal amount,
                              PaymentMethod method) throws PaymentFailedException {
        if (payments == null) throw new IllegalArgumentException("Payment service" +
                " must not be null.");
        if (amount == null || amount.signum() <= 0) throw new PaymentFailedException("Amount " +
                "must be > 0.");
        if (method == null) throw new PaymentFailedException("Payment method must not be null.");
        this.payments = payments;
        this.amount = amount;
        this.method = method;
    }

    public void charge() throws PaymentFailedException {
        payments.processPayment(amount, method);
    }

    public void commit() {
        this.committed = true;
    }

    @Override
    public void close() {
        if (committed) {
            return;
        }
        try {
            payments.refund(amount, method);
        } catch (PaymentFailedException e) {
            System.out.println("Refund failed during abort: " + e.getMessage());
        }
    }
}
