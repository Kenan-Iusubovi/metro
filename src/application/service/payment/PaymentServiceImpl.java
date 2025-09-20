package application.service.payment;

import application.exception.PaymentFailedException;
import application.port.PaymentService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.function.*;

public final class PaymentServiceImpl implements PaymentService {

    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000000.00");
    private static final double FAILURE_RATE = 0.2;
    private static final Random RANDOM = new Random();

    private boolean connected = false;

    private final BooleanSupplier connectionRetry = () -> {
        if (RANDOM.nextDouble() < FAILURE_RATE) {
            System.err.println("Connection failed, retrying...");
            return RANDOM.nextDouble() >= FAILURE_RATE;
        }
        return true;
    };

    private final Predicate<BigDecimal> amountValidator = amount ->
            amount != null &&
                    amount.compareTo(MIN_AMOUNT) >= 0 &&
                    amount.compareTo(MAX_AMOUNT) <= 0;

    private final Consumer<String> paymentLogger = message ->
            System.out.println("[PAYMENT LOG] " + LocalDateTime.now() + ": " + message);

    private final Supplier<String> transactionIdSupplier = () ->
            "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

    private final Function<BigDecimal, BigDecimal> amountNormalizer = amount ->
            amount.setScale(2, RoundingMode.HALF_UP);

    @Override
    public PaymentReceipt processPayment(BigDecimal amount, PaymentMethod method) throws PaymentFailedException {
        ensureConnectedOrThrow();
        BigDecimal normalized = normalizeAndValidate(amount);
        validateMethod(method);

        record PaymentRecord(String id, BigDecimal amt, PaymentMethod pm, TransactionType tt, LocalDateTime time, boolean success) {
            String getFormattedAmount() {
                return amt.setScale(2) + " GEL";
            }
        }

        PaymentRecord transaction = new PaymentRecord(
                transactionIdSupplier.get(), normalized, method, TransactionType.PAYMENT, LocalDateTime.now(), true
        );

        paymentLogger.accept("Processed payment: " + transaction.getFormattedAmount() + " via " + method.getPaymentDetails());

        return new PaymentReceipt(transaction.id(), TransactionType.PAYMENT, normalized, method, LocalDateTime.now());
    }

    @Override
    public PaymentReceipt refund(BigDecimal amount, PaymentMethod method) throws PaymentFailedException {
        ensureConnectedOrThrow();
        BigDecimal normalized = normalizeAndValidate(amount);
        validateMethod(method);

        record RefundRecord(String id, BigDecimal amt, PaymentMethod pm, TransactionType tt, LocalDateTime time, boolean success) {
            String getFormattedAmount() {
                return amt.setScale(2) + " GEL";
            }
            boolean isRefund() {
                return tt == TransactionType.REFUND;
            }
        }

        RefundRecord transaction = new RefundRecord(
                transactionIdSupplier.get(), normalized, method, TransactionType.REFUND, LocalDateTime.now(), true
        );

        paymentLogger.accept("Processed refund: " + transaction.getFormattedAmount() + " via " + method.getPaymentDetails());

        return new PaymentReceipt(transaction.id(), TransactionType.REFUND, normalized, method, LocalDateTime.now());
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
        return connectionRetry.getAsBoolean();
    }

    private BigDecimal normalizeAndValidate(BigDecimal amount) throws PaymentFailedException {
        if (!amountValidator.test(amount)) {
            throw new PaymentFailedException("Invalid amount: " + amount);
        }
        return amountNormalizer.apply(amount);
    }

    private void validateMethod(PaymentMethod method) throws PaymentFailedException {
        if (method == null) {
            throw new PaymentFailedException("Payment method must not be null");
        }
        paymentLogger.accept("Validated payment method: " + method.getPaymentDetails());
    }

    private String newId() {
        return UUID.randomUUID().toString();
    }
}