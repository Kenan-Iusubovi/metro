package application.service.payment;

import application.exception.PaymentFailedException;
import application.port.PaymentService;
import domain.payment.PaymentReceipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

public final class PaymentServiceImpl implements PaymentService {

    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000000.00");
    private static final double FAILURE_RATE = 0.2;

    private boolean connected = false;

    @Override
    public PaymentReceipt processPayment(BigDecimal amount,
                                         PaymentMethod paymentMethod) throws PaymentFailedException {
        ensureConnectionOrThrow();
        BigDecimal normalizedAmount = normalizeAndValidateAmount(amount);
        validatePaymentMethodOrThrow(paymentMethod, TransactionType.PAYMENT);

        return new PaymentReceipt(
                UUID.randomUUID(),
                TransactionType.PAYMENT,
                normalizedAmount,
                paymentMethod,
                LocalDateTime.now()
        );
    }

    @Override
    public PaymentReceipt refund(BigDecimal amount,
                                 PaymentMethod paymentMethod) throws PaymentFailedException {
        ensureConnectionOrThrow();
        BigDecimal normalizedAmount = normalizeAndValidateAmount(amount);
        validatePaymentMethodOrThrow(paymentMethod, TransactionType.REFUND);

        return new PaymentReceipt(
                UUID.randomUUID(),
                TransactionType.REFUND,
                normalizedAmount,
                paymentMethod,
                LocalDateTime.now()
        );
    }

    private boolean tryToConnect(BooleanSupplier connection) {
        return connection.getAsBoolean();
    }

    public void ensureConnectionOrThrow() throws PaymentFailedException {
        this.connected = tryToConnect(this::attemptConnection);

        if (!this.connected) {
            throw new PaymentFailedException("Unable to establish connection with payment server");
        }
    }

    private boolean attemptConnection() {
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            if (Math.random() > FAILURE_RATE) {
                return true;
            }
            attempts++;
            System.err.printf("Connection failed, retry number %d/%d%n", attempts, maxAttempts);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private boolean isAmountValid(BigDecimal amount, Predicate<BigDecimal> validator) {
        return validator.test(amount);
    }

    private BigDecimal normalizeAmount(BigDecimal amount, Function<BigDecimal, BigDecimal> normalizer) {

        BigDecimal processed = amount.abs();
        return normalizer.apply(processed);
    }

    private BigDecimal normalizeAndValidateAmount(BigDecimal amount) throws PaymentFailedException {
        Predicate<BigDecimal> amountValidator = value ->
                value != null &&
                        value.compareTo(MIN_AMOUNT) >= 0 &&
                        value.compareTo(MAX_AMOUNT) <= 0;

        if (!isAmountValid(amount, amountValidator)) {
            throw new PaymentFailedException("Invalid amount: " + amount);
        }

        return normalizeAmount(amount, value -> value.setScale(2, RoundingMode.HALF_UP));
    }

    private void logMessage(String message, TransactionType transactionType,
                            BiConsumer<String, TransactionType> logger) {
        logger.accept(message, transactionType);
    }

    public void validatePaymentMethodOrThrow(PaymentMethod paymentMethod,
                                             TransactionType transactionType) throws PaymentFailedException {
        if (paymentMethod == null) {
            throw new PaymentFailedException("Payment method is null!");
        }

        logMessage("Validated payment method: " + paymentMethod.getPaymentDetails(),
                transactionType,
                (message, type) -> System.out.printf("[%s LOG] %s : %s%n",
                        type.name(), LocalDateTime.now(), message));
    }
}
