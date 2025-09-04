package payment;

import java.time.LocalDateTime;
import java.util.Map;

public class PaymentService {

    private Map<LocalDateTime, String> paymentRecords;

    public void processPayment(double amount, PaymentMethodE method) {

        String text = "Processing payment of " + amount + " via " + method;

        paymentRecords.put(LocalDateTime.now(), text);

        System.out.println(text);

    }
}
