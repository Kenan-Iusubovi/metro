package application.service.payment;

import application.exception.PaymentFailedException;
import application.port.PaymentService;
import domain.payment.PaymentReceipt;

import java.math.BigDecimal;
import java.util.Random;

public final class PaymentSession implements AutoCloseable {

    private static final double FAILURE_RATE = 0.2;
    private static final Random RANDOM = new Random();

    private final PaymentService paymentService;
    private boolean connected = false;
    private boolean used = false;
    private String connectionId = null;

    public PaymentSession() throws PaymentFailedException {
        this.paymentService = new PaymentServiceImpl();
        if (!tryConnect()) {
            System.err.println("Initial connection failed, retrying...");
            if (!tryConnect()) {
                throw new PaymentFailedException("Unable to establish" +
                        " connection to payment service");
            }
        }
    }

    public PaymentReceipt processPayment(BigDecimal amount,
                                         PaymentMethod method) throws PaymentFailedException {
        validateSessionState();
        ensureConnected();
        if (amount == null) {
            throw new PaymentFailedException("Amount must not be null");
        }
        if (method == null) {
            throw new PaymentFailedException("Payment method must not be null");
        }
        PaymentReceipt receipt = paymentService.processPayment(amount, method);
        used = true;
        return receipt;
    }

    public PaymentReceipt processRefund(BigDecimal amount,
                                        PaymentMethod method) throws PaymentFailedException {
        validateSessionState();
        ensureConnected();
        if (amount == null) {
            throw new PaymentFailedException("Amount must not be null");
        }
        if (method == null) {
            throw new PaymentFailedException("Payment method must not be null");
        }
        PaymentReceipt receipt = paymentService.refund(amount, method);
        used = true;
        return receipt;
    }

    private boolean tryConnect() {
        if (RANDOM.nextDouble() < FAILURE_RATE) {
            connected = false;
            connectionId = null;
            return false;
        }
        connected = true;
        connectionId = "PS-" + System.nanoTime() + "-" + RANDOM.nextInt(1000);
        return true;
    }

    private void ensureConnected() throws PaymentFailedException {
        if (!connected) {
            throw new PaymentFailedException("Not connected to payment service");
        }
    }

    private void validateSessionState() throws PaymentFailedException {
        if (used) {
            throw new PaymentFailedException("Payment session has already been used and closed");
        }
    }

    @Override
    public void close() {
        if (connected) {
            connected = false;
            connectionId = null;
        }
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getConnectionId() {
        return connectionId;
    }
}
