package application.port;

import application.exception.PaymentFailedException;
import application.service.payment.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentService {

    void processPayment(BigDecimal amount, PaymentMethod method) throws PaymentFailedException;

    void refund(BigDecimal amount, PaymentMethod method) throws PaymentFailedException;

}
