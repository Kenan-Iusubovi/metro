package application.port;

import application.service.payment.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentService {

    void processPayment(BigDecimal amount, PaymentMethod method);
    void refund(BigDecimal amount, PaymentMethod method);

}
