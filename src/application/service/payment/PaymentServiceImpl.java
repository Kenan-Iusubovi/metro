package application.service.payment;

import application.exception.PaymentFailedException;
import application.port.PaymentService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public final class PaymentServiceImpl implements PaymentService {

    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000000.00");
    private static final double FAILURE_RATE = 0.2;
    private static final Random RANDOM = new Random();

    private boolean connected = false;

    @Override
    public PaymentReceipt processPayment(BigDecimal amount,
                                         PaymentMethod method) throws PaymentFailedException {
        ensureConnectedOrThrow();
        BigDecimal normalized = normalizeAndValidate(amount);
        validateMethod(method);
        return new PaymentReceipt(newId(), TransactionType.PAYMENT, normalized, method, LocalDateTime.now());
    }

    @Override
    public PaymentReceipt refund(BigDecimal amount,
                                 PaymentMethod method) throws PaymentFailedException {
        ensureConnectedOrThrow();
        BigDecimal normalized = normalizeAndValidate(amount);
        validateMethod(method);
        return new PaymentReceipt(newId(), TransactionType.REFUND,
                normalized, method, LocalDateTime.now());
    }

    private void ensureConnectedOrThrow() throws PaymentFailedException {
        if (connected) {
            return;
        }
        if (!connectWithFallback()) {
            throw new PaymentFailedException("Unable to establish connection to payment service");
        }
    }

    private boolean connectWithFallback() {
        if (RANDOM.nextDouble() < FAILURE_RATE) {
            System.err.println("Connection failed, retrying...");
            if (RANDOM.nextDouble() < FAILURE_RATE) {
                connected = false;
                return false;
            }
        }
        connected = true;
        return true;
    }

    private BigDecimal normalizeAndValidate(BigDecimal amount) throws PaymentFailedException {
        if (amount == null) {
            throw new PaymentFailedException("Amount must not be null");
        }
        BigDecimal scaled = amount.setScale(2, RoundingMode.HALF_UP);
        if (scaled.compareTo(MIN_AMOUNT) < 0) {
            throw new PaymentFailedException("Amount must be at least " + MIN_AMOUNT);
        }
        if (scaled.compareTo(MAX_AMOUNT) > 0) {
            throw new PaymentFailedException("Amount must not exceed " + MAX_AMOUNT);
        }
        return scaled;
    }

    private void validateMethod(PaymentMethod method) throws PaymentFailedException {
        if (method == null) {
            throw new PaymentFailedException("Payment method must not be null");
        }
    }

    private String newId() {
        return UUID.randomUUID().toString();
    }
}
