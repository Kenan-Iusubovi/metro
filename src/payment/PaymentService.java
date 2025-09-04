package payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {

    private static final int MAX_RECORDS = 100;

    private LocalDateTime[] timestamps;

    private String[] texts;

    private int size;

    public PaymentService() {

        this.timestamps = new LocalDateTime[MAX_RECORDS];
        this.texts = new String[MAX_RECORDS];
        this.size = 0;
    }

    public void processPayment(BigDecimal amount, PaymentMethodE method) {

        if (amount == null || amount.signum() < 0) throw new IllegalArgumentException("amount");
        if (method == null) throw new IllegalArgumentException("method");
        record("PAY " + amount + " " + method);
    }

    public void refund(BigDecimal amount, PaymentMethodE method) {

        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount");
        if (method == null) throw new IllegalArgumentException("method");
        record("REFUND " + amount + " " + method);
    }

    public void auditLine(String text) {

        if (text == null) text = "";
        record("AUDIT " + text);
    }

    private void record(String text) {

        if (size < MAX_RECORDS) {
            timestamps[size] = LocalDateTime.now();
            texts[size] = text;
            size++;
        }
        System.out.println(text);
        System.out.println();
    }

    public LocalDateTime[] getTimestamps() {

        return timestamps;
    }

    public void setTimestamps(LocalDateTime[] timestamps) {

        if (timestamps == null)
            throw new IllegalArgumentException("timestamps");

        this.timestamps = timestamps;
    }

    public String[] getTexts() {

        return texts;
    }

    public void setTexts(String[] texts) {

        if (texts == null)
            throw new IllegalArgumentException("texts");

        this.texts = texts;
    }

    public int getSize() {

        return size;
    }

    public void setSize(int size) {

        if (size < 0 || size > MAX_RECORDS)
            throw new IllegalArgumentException("size");

        this.size = size;
    }
}
