package com.solvd.metro.application.service.payment;

import com.solvd.metro.application.exception.PaymentFailedException;
import com.solvd.metro.application.port.PaymentService;
import com.solvd.metro.domain.paymentrecord.PaymentReceipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

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
            logger.error("Unable to establish connection with payment server");
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
            logger.error("Connection failed, retry number {}/{}}", attempts, maxAttempts);
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
            logger.error("Invalid amount: {}", amount);
            throw new PaymentFailedException("Invalid amount: " + amount);
        }

        return normalizeAmount(amount, value -> value.setScale(2, RoundingMode.HALF_UP));
    }

    private void logMessage(String message, TransactionType transactionType,
                            BiConsumer<String, TransactionType> loggerImpl) {
        loggerImpl.accept(message, transactionType);
    }

    public void validatePaymentMethodOrThrow(PaymentMethod paymentMethod,
                                             TransactionType transactionType) throws PaymentFailedException {
        if (paymentMethod == null) {
            logger.error("Payment method is null!");
            throw new PaymentFailedException("Payment method is null!");
        }

        logMessage("Validated payment method: " + paymentMethod.getPaymentDetails(),
                transactionType,
                (message, type) -> logger.info("[{} LOG] {} : {}",
                        type.name(), LocalDateTime.now(), message));


    }
}
