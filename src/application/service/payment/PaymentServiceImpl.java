package application.service.payment;

import application.exception.PaymentFailedException;
import application.port.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class PaymentServiceImpl implements PaymentService {

    private static final int MAX_RECORDS = 100;

    private final LocalDateTime[] timestamps = new LocalDateTime[MAX_RECORDS];
    private final String[] texts = new String[MAX_RECORDS];
    private int size = 0;
    private long writeIndex = 0;

    @Override
    public void processPayment(BigDecimal amount, PaymentMethod method) throws PaymentFailedException {
        if (amount == null || amount.signum() <= 0) {
            throw new PaymentFailedException("Amount must be > 0.");
        }
        if (method == null) {
            throw new PaymentFailedException("Payment method must not be null.");
        }
        record("PAY " + amount + " " + method);
    }

    @Override
    public void refund(BigDecimal amount, PaymentMethod method) throws PaymentFailedException {
        if (amount == null || amount.signum() <= 0) {
            throw new PaymentFailedException("Amount must be > 0.");
        }
        if (method == null) {
            throw new PaymentFailedException("Payment method must not be null.");
        }
        record("REFUND " + amount + " " + method);
    }

    private void record(String text) {
        int idx = (int) (writeIndex % MAX_RECORDS);
        timestamps[idx] = LocalDateTime.now();
        texts[idx] = text;
        writeIndex++;
        if (size < MAX_RECORDS) size++;
        System.out.println("[" + timestamps[idx] + "] " + text + "\n");
    }

    public LocalDateTime[] getTimestamps() {
        LocalDateTime[] out = new LocalDateTime[size];
        for (int i = 0; i < size; i++) {
            int src = (int) ((writeIndex - 1 - i + MAX_RECORDS) % MAX_RECORDS);
            out[i] = timestamps[src];
        }
        return out;
    }

    public String[] getTexts() {
        String[] out = new String[size];
        for (int i = 0; i < size; i++) {
            int src = (int) ((writeIndex - 1 - i + MAX_RECORDS) % MAX_RECORDS);
            out[i] = texts[src];
        }
        return out;
    }

    public void setHistory(LocalDateTime[] newTimestamps, String[] newTexts) throws PaymentFailedException {
        if (newTimestamps == null || newTexts == null) {
            throw new PaymentFailedException("History arrays must not be null.");
        }
        if (newTimestamps.length != newTexts.length) {
            throw new PaymentFailedException("History arrays must have the same length.");
        }
        if (newTimestamps.length > MAX_RECORDS) {
            throw new PaymentFailedException("History length must be <= " + MAX_RECORDS + ".");
        }
        Arrays.fill(timestamps, null);
        Arrays.fill(texts, null);
        for (int i = 0; i < newTimestamps.length; i++) {
            timestamps[i] = newTimestamps[i];
            texts[i] = newTexts[i];
        }
        size = newTimestamps.length;
        writeIndex = size;
    }
}
